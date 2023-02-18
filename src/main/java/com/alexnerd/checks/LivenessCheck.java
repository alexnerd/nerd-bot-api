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

package com.alexnerd.checks;

import com.alexnerd.control.Storage;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.nio.file.Files;

@ApplicationScoped
public class LivenessCheck {

    @Inject
    Storage storage;

    @Produces
    @Liveness
    public HealthCheck checkContentDirectoryExists() {
        return () -> HealthCheckResponse
                .named("content-directory-exists")
                .status(storage.isContentDirectoryExists())
                .build();
    }

    @Produces
    @Liveness
    public HealthCheck checkImageDirectoryExists() {
        return () -> HealthCheckResponse
                .named("image-directory-exists")
                .status(storage.isImageDirectoryExists())
                .build();
    }
}
