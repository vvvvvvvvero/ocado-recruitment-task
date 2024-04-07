package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Getter @Setter
public class DataService {
    String absolutePath;
    Map<String, List<String>> productsToCarriersMap = new HashMap<>();
    Map<String, Integer> carriersRankingMap = new HashMap<>();
    public DataService(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public void loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File srcJsonFile = new File(absolutePath);
            TypeReference<Map<String, List<String>>> typeRef = new TypeReference<>() {};
            productsToCarriersMap = objectMapper.readValue(srcJsonFile, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printProductsToCarrierData() {
        for (Map.Entry<String, List<String>> entry : productsToCarriersMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public void countCarriersRanking() {
        for (List<String> carriers: productsToCarriersMap.values()) {
            for (String carrier : carriers) {
                carriersRankingMap.merge(carrier, 1, Integer::sum);
            }
        }
    }

    public void printCarriersRankingData() {
        Map<String, Integer> sortedCarriersRankingMap = new LinkedHashMap<>();
        carriersRankingMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedCarriersRankingMap.put(x.getKey(), x.getValue()));
        for (Map.Entry<String, Integer> entry : sortedCarriersRankingMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        DataService dataManager = new DataService("/Users/veraemelianova/IdeaProjects/ocado-task/src/main/resources/config.json");
        dataManager.loadData();
        dataManager.printProductsToCarrierData();
        dataManager.countCarriersRanking();
        dataManager.printCarriersRankingData();


    }
}
