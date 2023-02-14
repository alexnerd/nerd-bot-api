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

import com.alexnerd.control.TelegramBot;
import org.jboss.resteasy.reactive.RestForm;

public interface MessageCollection extends Executable {

    record TextMsg(String message) implements MessageCollection {
        @Override
        public void execute(TelegramBot bot) {
            bot.send(this);
        }
    }

    record PhotoWithCaptionMsg(@RestForm byte[] photo, @RestForm String caption) implements MessageCollection {
        @Override
        public void execute(TelegramBot bot) {
            bot.send(this);
        }
    }

    record PollMsg(String question, boolean isAnonymous, boolean isMultiple, String options) implements MessageCollection {
        @Override
        public void execute(TelegramBot bot) {
            bot.send(this);
        }
    }

    record QuizMsg(String question, int correctOption, boolean isAnonymous, boolean isMultiple,
                   String options, String explanation) implements MessageCollection {
        @Override
        public void execute(TelegramBot bot) {
            bot.send(this);
        }
    }
}
