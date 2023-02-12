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

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class Storage {

    @ConfigProperty(name = "storage.content.dir")
    String contentDir;

    @ConfigProperty(name = "storage.image.dir")
    String imageDir;

    Path storageContentPath;
    Path storageImagePath;

    @PostConstruct
    public void init() {
        storageContentPath = Path.of(contentDir);
        storageImagePath = Path.of(imageDir);
    }

    public byte[] getImage(String path) {
        try {
            return Files.readAllBytes(storageImagePath.resolve(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getRandomFile() {
        try {
            List<Path> paths = Files.list(storageContentPath).toList();

            Random r = new Random();
            Path path = paths.get(r.nextInt(paths.size()));

            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
