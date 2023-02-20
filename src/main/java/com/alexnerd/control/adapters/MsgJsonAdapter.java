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
import com.alexnerd.control.factory.message.MessageFactory;
import com.alexnerd.control.factory.message.MessageFactoryProvider;
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
    public MessageCollection adaptFromJson(JsonObject json) {
        String type = json.getString("type");
        MessageType messageType = MessageType.valueOf(type);
        MessageFactory factory = MessageFactoryProvider.getFactory(messageType);

        JsonObject message = json.getJsonObject("message");
        return factory.create(message, storage);
    }
}
