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

package com.alexnerd.control.factory.message;

import com.alexnerd.control.Storage;
import com.alexnerd.entity.MessageCollection;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import java.util.stream.Collectors;

public class PollMsgFactory extends MessageFactory{
    @Override
    public MessageCollection create(JsonObject json, Storage storage) {
        String question = json.getString("question");
        boolean  isAnonymous = json.getBoolean("is_anonymous");
        boolean  isMultiple = json.getBoolean("allows_multiple_answers");

        JsonArray jsonArray = json.getJsonArray("options");
        String options = jsonArray.stream().map(JsonValue::toString).collect(Collectors.joining(","));

        return new MessageCollection.PollMsg(question, isAnonymous, isMultiple, "[" + options + "]");
    }
}
