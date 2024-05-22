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
import com.alexnerd.entity.Message;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collection;
import java.util.stream.StreamSupport;

public class QuizMsgFactory extends MessageFactory{
    @Override
    public Message create(JsonNode json, Storage storage) {
        String type = json.get("type").asText();
        String question = json.get("question").asText();
        int correctOption = json.get("correct_option_id").asInt();
        boolean  isAnonymous = json.get("is_anonymous").asBoolean();
        boolean  isMultiple = json.get("allows_multiple_answers").asBoolean();
        String explanation = json.get("explanation").isNull() ? null : json.get("explanation").asText();

        Iterable<JsonNode> iterable = () -> json.get("options").iterator();

        Collection<String> options = StreamSupport.stream(iterable.spliterator(), false).
                map(JsonNode::asText)
                .toList();

        return new Message.QuizMsg(type, question, correctOption, isAnonymous, isMultiple, options, explanation);
    }
}
