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

import javax.ws.rs.core.Response;

public interface RequestCollection {

    Response send(TelegramBot bot);

    record TextRq(String message) implements RequestCollection {
        @Override
        public Response send(TelegramBot bot) {
            return bot.send(this);
        }
    }

    record PhotoWithCaptionRq(@RestForm byte[] photo, @RestForm String caption) implements RequestCollection {
        @Override
        public Response send(TelegramBot bot) {
            return bot.send(this);
        }
    }
}
