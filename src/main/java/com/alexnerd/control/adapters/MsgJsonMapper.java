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
import com.alexnerd.entity.Message;
import com.alexnerd.entity.MessageType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MsgJsonMapper {
    @Inject
    Storage storage;

    public Message convert(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);

        MessageType messageType = MessageType.valueOf(jsonNode.get("type").asText());
        MessageFactory factory = MessageFactoryProvider.getFactory(messageType);

        JsonNode message = jsonNode.get("message");
        return factory.create(message, storage);
    }
}
