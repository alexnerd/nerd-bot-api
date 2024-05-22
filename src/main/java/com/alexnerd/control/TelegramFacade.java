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

import com.alexnerd.entity.Message;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class TelegramFacade {

    @ConfigProperty(name = "app.telegram.chatId")
    String chatId;

    @Inject
    @RestClient
    TelegramRestClient telegramClient;

    public Response send(Message.TextMsg msg) {
        Log.debug("Send text message: " + msg.message());
        return telegramClient.sendMessage(chatId, msg.message());
    }

    public Response send(Message.PhotoWithCaptionMsg msg) {
        Log.debug("Send photo with caption message: " + msg.caption());
        return telegramClient.sendPhoto(chatId, msg);
    }

    public Response send(Message.PollMsg msg) {
        Log.debug("Send poll message: " + msg.question());
        return telegramClient.sendPoll(chatId, msg);
    }

    public Response send(Message.QuizMsg msg) {
        Log.debug("Send quiz message: " + msg.question());
        return telegramClient.sendQuiz(chatId, msg);
    }
}
