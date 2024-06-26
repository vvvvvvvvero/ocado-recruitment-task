package org.example.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE )
public class ShoppingCart {
    List<String> cartProducts;
    List<String> cartCarriers;
    Map<String, List<String>> cartMap = new HashMap<>();
    Map<String, Integer> carriersRank = new HashMap<>();

    public ShoppingCart(List<String> cartProducts) {
        if (cartProducts == null || cartProducts.isEmpty()) {
            throw new IllegalArgumentException("List of products cannot be null or empty");
        }
        this.cartProducts = cartProducts;
    }

    public void prepareCart(Map<String, List<String>> productsToCarriersMap) {
        populateCartMap(productsToCarriersMap);
        populateCartCarriers();
        countCarriersRanking();
    }

    private void populateCartMap(Map<String, List<String>> productToCarrierMap) {
        for (String product : cartProducts) {
            List<String> carriers = productToCarrierMap.get(product);
            if (carriers == null) {
                throw new IllegalArgumentException("Product " + product + " is not present in the configuration file");
            }
            cartMap.put(product, carriers);
        }
    }

    private void populateCartCarriers() {
        Set<String> uniqueCarriers = new HashSet<>();
        for (List<String> carriers : cartMap.values()) {
            uniqueCarriers.addAll(carriers);
        }
        cartCarriers = new ArrayList<>(uniqueCarriers);
    }

    private void countCarriersRanking() {
        for (List<String> carriers : cartMap.values()) {
            for (String carrier : carriers) {
                carriersRank.merge(carrier, 1, Integer::sum);
            }
        }
    }

    public int getNumberOfProducts() {
        return cartProducts.size();
    }

    public int getNumberOfCarriers() {
        return cartCarriers.size();
    }

}
