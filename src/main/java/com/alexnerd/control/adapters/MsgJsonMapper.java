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

import com.alexnerd.entity.MessageCollection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

@ApplicationScoped
public class MsgJsonMapper {

    @Inject
    MsgJsonAdapter adapter;

    JsonbConfig config;

    @PostConstruct
    public void init() {
        config = new JsonbConfig()
                .withFormatting(true)
                .withAdapters(adapter);
    }

    public MessageCollection load(String stringified) {
        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            return jsonb.fromJson(stringified, MessageCollection.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
