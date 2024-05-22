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

package com.alexnerd.entity;

import com.alexnerd.control.TelegramFacade;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Collection;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
sealed public interface Message extends Executable {

    record TextMsg(String message) implements Message {
        @Override
        public void execute(TelegramFacade facade) {
            facade.send(this);
        }
    }

    record PhotoWithCaptionMsg(byte[] photo, String caption) implements Message {
        @Override
        public void execute(TelegramFacade facade) {
            facade.send(this);
        }
    }

    record PollMsg(String question, boolean isAnonymous, boolean isMultiple,
                   Collection<String> options) implements Message {
        @Override
        public void execute(TelegramFacade facade) {
            facade.send(this);
        }
    }

    record QuizMsg(String type, String question, int correctOptionId, boolean isAnonymous,
                   boolean isMultiple, Collection<String> options, String explanation) implements Message {
        @Override
        public void execute(TelegramFacade facade) {
            facade.send(this);
        }
    }
}
