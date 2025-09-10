package org.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class JsonReader {

    private static final ObjectMapper mapper = JsonWriter.mapper;

    public static <T> T readFromJsonFile(Path filePath, Class<T> clazz) {
        try {
            return mapper.readValue(new File(filePath.toUri()), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON from: " + filePath + " Exception: " + e);
        }
    }

}
