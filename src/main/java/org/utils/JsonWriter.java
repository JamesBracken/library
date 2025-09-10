package org.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonWriter {
    static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static void writeToJsonFile(Object data, Path filePath) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath.toUri()), data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write JSON to: " + filePath + " Exception: " + e);
        }
    }

    public static <T> void appendToJsonFile(T newData, Path filePath, Class<T[]> clazz) {
        try {
            File file = filePath.toFile();
            ObjectMapper mapper = JsonWriter.mapper;

            T[] existingData = file.exists() && file.length() > 0
                    ? mapper.readValue(file, clazz)
                    : (T[]) java.lang.reflect.Array.newInstance(clazz.getComponentType(), 0);

            List<T> dataList = new ArrayList<>(Arrays.asList(existingData));
            dataList.add(newData);

            writeToJsonFile(dataList, filePath);

        } catch (IOException e) {
            throw new RuntimeException("Failed to append JSON to: " + filePath + " Exception: " + e);
        }
    }
}