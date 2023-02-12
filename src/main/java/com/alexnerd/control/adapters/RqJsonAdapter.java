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
import com.alexnerd.entity.RequestCollection;
import com.alexnerd.entity.RequestType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import javax.naming.OperationNotSupportedException;

@ApplicationScoped
public class RqJsonAdapter implements JsonbAdapter<RequestCollection, JsonObject> {

    @Inject
    Storage storage;

    @Override
    public JsonObject adaptToJson(RequestCollection rq) throws Exception {
        throw new OperationNotSupportedException("Converting operation from BaseRq to Json not supported");
    }

    @Override
    public RequestCollection adaptFromJson(JsonObject jsonObject) {
        String type = jsonObject.getString("type");
        return switch (RequestType.valueOf(type)) {
            case TEXT -> {
                JsonObject message = jsonObject.getJsonObject("message");
                String text = message.getString("text");
                yield new RequestCollection.TextRq(text);
            }
            case IMAGE_WITH_CAPTION -> {
                JsonObject message = jsonObject.getJsonObject("message");
                String text = message.getString("text");
                String imgSource = message.getString("img_source");
                byte[] photo = storage.getPhoto(imgSource);
                yield new RequestCollection.PhotoWithCaptionRq(photo, text);
            }
            default -> throw new IllegalStateException("Unsupported request type: " + RequestType.valueOf(type));
        };
    }
}
