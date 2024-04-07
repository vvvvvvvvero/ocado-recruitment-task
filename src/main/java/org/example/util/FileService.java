package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class FileService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, List<String>> readConfigFile(String absoluteConfigPath) {
        File file = readFile(absoluteConfigPath);
        try {
            return objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading file at " + absoluteConfigPath + ": " + e.getMessage(), e);
        }
    }

    public List<String> readCartFile(String absoluteCartPath) {
        File file = readFile(absoluteCartPath);
        try {
            return objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading file at " + absoluteCartPath + ": " + e.getMessage(), e);
        }
    }

    private File readFile(String absolutePath) {
        if (absolutePath == null) {
            throw new IllegalArgumentException("Path variable cannot be null");
        }
        File file = new File(absolutePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }
        return file;
    }

    public static void main(String[] args) {
        FileService fileService = new FileService();
        Map<String, List<String>> productsToCarriersMap = fileService.readConfigFile("/Users/veraemelianova/IdeaProjects/ocado-task/src/main/resources/config.json");
        System.out.println(productsToCarriersMap);
        List<String> products = fileService.readCartFile("/Users/veraemelianova/IdeaProjects/ocado-task/src/main/resources/basket-1.json");
        System.out.println(products);
    }

}
