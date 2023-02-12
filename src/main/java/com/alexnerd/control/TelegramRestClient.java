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

import com.alexnerd.entity.RequestCollection;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface TelegramRestClient {

    @GET
    @Path("/sendMessage")
    @Consumes(MediaType.WILDCARD)
    Response sendMessage(@QueryParam("chat_id") String chatId, @QueryParam("text") String message);

    @POST
    @Path("/sendPhoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response sendPhoto(@QueryParam("chat_id") String chatId, RequestCollection.PhotoWithCaptionRq rq);


    @ClientExceptionMapper
    static RuntimeException toException(Response response) {
        JsonObject json = response.readEntity(JsonObject.class);
        return new RuntimeException("Remote server responded not ok with error message: " + json.getString("description"));
    }
}
