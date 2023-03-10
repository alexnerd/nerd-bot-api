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

package com.alexnerd.control;

import com.alexnerd.entity.MessageCollection;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class TelegramBot {

    @ConfigProperty(name = "telegram.chatId")
    String chatId;

    @Inject
    @RestClient
    TelegramRestClient telegramClient;

    public Response send(MessageCollection.TextMsg msg) {
        return telegramClient.sendMessage(chatId, msg.message());
    }

    public Response send(MessageCollection.PhotoWithCaptionMsg msg) {
        return telegramClient.sendPhoto(chatId, msg);
    }

    public Response send(MessageCollection.PollMsg msg) {
        return telegramClient.sendPoll(chatId, msg.question(), msg.isAnonymous(), msg.isMultiple(), msg.options());
    }

    public Response send(MessageCollection.QuizMsg msg) {
        return telegramClient.sendQuiz(chatId, "quiz", msg.question(), msg.correctOption(), msg.isAnonymous(),
                msg.isMultiple(), msg.options(), msg.explanation());
    }
}
