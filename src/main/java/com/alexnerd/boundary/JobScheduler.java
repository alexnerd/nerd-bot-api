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
import com.alexnerd.control.TelegramBot;
import com.alexnerd.control.adapters.MsgJsonMapper;
import com.alexnerd.entity.MessageCollection;
import io.quarkus.scheduler.Scheduled;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class JobScheduler {

    @Inject
    MsgJsonMapper mapper;

    @Inject
    TelegramBot bot;

    @Inject
    Storage storage;

    @Scheduled(every = "15s")
    protected void sendRandomMessage() {
        String randomFile = storage.getRandomFile();
        MessageCollection msg = mapper.load(randomFile);
        msg.execute(bot);
    }
}
