package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Product;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String absolutePath;

    public FileService(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public List<Product> readProducts() {
        File file = readFile();
        try {
            Map<String, List<String>> productsMap = objectMapper.readValue(file, new TypeReference<>() {});
            List<Product> products = new ArrayList<>();
            productsMap.forEach((productName, carriers) -> products.add(new Product(productName, carriers)));
            return products;
        } catch (IOException e) {
            throw new RuntimeException("Could not read the products from file " + absolutePath);
        }
    }

    public File readFile() {
        if (absolutePath == null) {
            throw new IllegalArgumentException("Path variable cannot be null");
        }
        File file = new File(absolutePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }
        return file;
    }

}
