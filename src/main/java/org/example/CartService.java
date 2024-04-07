package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.*;

@Getter @Setter
public class CartService {
    List<String> cartProducts;
    List<String> cartCarriers;
    Map<String, List<String>> cartMap = new HashMap<>();
    Map<String, Integer> carriersRanking = new HashMap<>();
    DataService dataService;

    public CartService(DataService dataService) {
        this.dataService = dataService;
        dataService.loadData();
    }

    public void loadProductsFromFile(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(path);
        try {
            cartProducts = objectMapper.readValue(file, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading file at " + path + ": " + e.getMessage(), e);
        }
    }

    public void createCartMap() {
        for (String product : cartProducts) {
            cartMap.put(product, dataService.getProductsToCarriersMap().get(product));
        }
        cartCarriers = new ArrayList<>(cartMap.values().stream().flatMap(Collection::stream).distinct().toList());
    }

    public void countCarriersRanking() {
        for (List<String> carriers: cartMap.values()) {
            for (String carrier : carriers) {
                carriersRanking.merge(carrier, 1, Integer::sum);
            }
        }
    }

    public int getNumberOfProducts() {
        return cartProducts.size();
    }

    public int getNumberOfCarriers() {
        return cartCarriers.size();
    }


    public Map<Integer, String> getIndexedProducts() {
        Map<Integer, String> indexedProducts = new HashMap<>();
        for (int i = 0; i < cartProducts.size(); i++) {
            indexedProducts.put(i, cartProducts.get(i));
        }
        return indexedProducts;
    }

    public Map<Integer, String> getIndexedCarriers() {
        Map<Integer, String> indexedCarriers = new HashMap<>();
        for (int i = 0; i < cartCarriers.size(); i++) {
            indexedCarriers.put(i, cartCarriers.get(i));
        }
        return indexedCarriers;
    }

    public Map<Integer, Integer> getIndexedCarriersRanking() {
        Map<Integer, Integer> carriersRanking = new HashMap<>();
        for (int i = 0; i < cartCarriers.size(); i++) {
            carriersRanking.put(i, getCarriersRanking().get(cartCarriers.get(i)));
        }
        return carriersRanking;
    }

    public Map<Integer, Set<Integer>> getIndexedProductsToAllowedCarriers() {
        Map<Integer, Set<Integer>> indexedProductsToAllowedCarriers = new HashMap<>();
        for (int i = 0; i < cartProducts.size(); i++) {
            Set<Integer> allowedCarriers = new HashSet<>();
            for (String carrier : cartMap.get(cartProducts.get(i))) {
                allowedCarriers.add(cartCarriers.indexOf(carrier));
            }
            indexedProductsToAllowedCarriers.put(i, allowedCarriers);
        }
        return indexedProductsToAllowedCarriers;
    }

    public static void main(String[] args) {
        DataService dataService = new DataService("/Users/veraemelianova/IdeaProjects/ocado-task/src/main/resources/config.json");
        CartService cartService = new CartService(dataService);
        cartService.loadProductsFromFile("/Users/veraemelianova/IdeaProjects/ocado-task/src/main/resources/basket-1.json");
        System.out.println(cartService.getCartProducts());
        cartService.createCartMap();
        System.out.println(cartService.getCartMap());
        cartService.countCarriersRanking();
        System.out.println(cartService.getCarriersRanking());

    }

}
