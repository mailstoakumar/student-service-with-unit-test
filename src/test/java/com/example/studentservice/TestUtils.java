package com.example.studentservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String jsonFilePath = "src/test/resources/data/";

    public static String readJsonFile(String fileName) throws IOException {

        return new String(Files.readAllBytes(Paths.get(jsonFilePath+fileName)));
    }

    public static String getStringFormattedJsonFile(String fileName) throws IOException{
        return new String(Files.readAllBytes(Paths.get(jsonFilePath+fileName)));
    }

    public static Object readJsonFileAsObject(String filePath, Class<?> valueType) throws IOException {
        return objectMapper.readValue(Files.readAllBytes(Paths.get(filePath)), valueType);
    }
}

