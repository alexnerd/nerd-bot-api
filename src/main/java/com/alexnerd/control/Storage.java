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

import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@ApplicationScoped
public class Storage {

    @ConfigProperty(name = "storage.content.dir")
    String contentDir;

    @ConfigProperty(name = "storage.image.dir")
    String imageDir;

    private Path storageContentPath;
    private Path storageImagePath;

    private final static String FILE_EXTENSION = ".json";

    @PostConstruct
    public void init() {
        storageContentPath = Path.of(contentDir);
        storageImagePath = Path.of(imageDir);
    }

    public byte[] readImageFile(String path) {
        try {
            return Files.readAllBytes(storageImagePath.resolve(path));
        } catch (IOException e) {
            Log.error("Read image file error, path: " + path, e);
            throw new RuntimeException(e);
        }
    }

    private String readJsonFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            Log.error("Read json file error, path: " + path, e);
            throw new RuntimeException(e);
        }
    }

    public String readRandomJsonFile() {
        try (Stream<Path> pathStream = Files.list(storageContentPath)) {
            List<Path> paths = pathStream
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(FILE_EXTENSION))
                    .toList();
            Path path = paths.get(ThreadLocalRandom.current().nextInt(paths.size()));

            return readJsonFile(path);
        } catch (IOException e) {
            Log.error("Read random json file error", e);
            throw new RuntimeException(e);
        }
    }

    public boolean isContentDirectoryExists() {
        return Files.exists(storageContentPath);
    }

    public boolean isImageDirectoryExists() {
        return Files.exists(storageImagePath);
    }
}
