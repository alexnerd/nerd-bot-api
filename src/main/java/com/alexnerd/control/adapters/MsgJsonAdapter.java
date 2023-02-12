/*
 * Copyright 2023 Aleksey Popov <alexnerd.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.alexnerd.control.adapters;

import com.alexnerd.control.Storage;
import com.alexnerd.entity.MessageCollection;
import com.alexnerd.entity.MessageType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import javax.naming.OperationNotSupportedException;

@ApplicationScoped
public class MsgJsonAdapter implements JsonbAdapter<MessageCollection, JsonObject> {

    @Inject
    Storage storage;

    @Override
    public JsonObject adaptToJson(MessageCollection msg) throws Exception {
        throw new OperationNotSupportedException("Converting operation from MessageCollection to Json not supported");
    }

    @Override
    public MessageCollection adaptFromJson(JsonObject jsonObject) {
        String type = jsonObject.getString("type");
        return switch (MessageType.valueOf(type)) {
            case TEXT -> {
                JsonObject message = jsonObject.getJsonObject("message");
                String text = message.getString("text");
                yield new MessageCollection.TextMsg(text);
            }
            case IMAGE_WITH_CAPTION -> {
                JsonObject message = jsonObject.getJsonObject("message");
                String text = message.getString("text");
                String imgSource = message.getString("img_source");
                byte[] photo = storage.getImage(imgSource);
                yield new MessageCollection.PhotoWithCaptionMsg(photo, text);
            }
            default -> throw new IllegalStateException("Unsupported message type: " + MessageType.valueOf(type));
        };
    }
}
