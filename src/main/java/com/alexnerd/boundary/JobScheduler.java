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

package com.alexnerd.boundary;

import com.alexnerd.control.Storage;
import com.alexnerd.control.TelegramFacade;
import com.alexnerd.control.adapters.MsgJsonMapper;
import com.alexnerd.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.scheduler.Scheduled;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class JobScheduler {

    @Inject
    MsgJsonMapper mapper;

    @Inject
    TelegramFacade facade;

    @Inject
    Storage storage;

    @Scheduled(every = "${app.scheduler.timer.randommessage}")
    protected void sendRandomMessage() {
        String json = storage.readRandomJsonFile();
        try {
            Message msg = mapper.convert(json);
            msg.execute(facade);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
