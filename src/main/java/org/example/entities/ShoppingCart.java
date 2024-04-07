package org.example.entities;

import lombok.Getter;
import lombok.Setter;
import org.example.util.FileService;

import java.util.*;

@Getter
@Setter
public class ShoppingCart {
    private List<String> cartProducts;
    private List<String> cartCarriers;
    private Map<String, List<String>> cartMap = new HashMap<>();
    private Map<String, Integer> carriersRank = new HashMap<>();

    public ShoppingCart(List<String> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public void prepareCart(Map<String, List<String>> productsToCarriersMap) {
        populateCartMap(productsToCarriersMap);
        populateCartCarriers();
        countCarriersRanking();
    }

    private void populateCartMap(Map<String, List<String>> productToCarrierMap) {
        List<String> productsToRemove = new ArrayList<>();
        for (String product : cartProducts) {
            List<String> carriers = productToCarrierMap.get(product);
            if (carriers != null) {
                cartMap.put(product, carriers);
            } else {
                productsToRemove.add(product);
                System.out.println("Product " + product + " is not available and will not be added to the cart.");
            }
        }
        cartProducts.removeAll(productsToRemove);
    }

    private void populateCartCarriers() {
        Set<String> uniqueCarriers = new HashSet<>();
        for (List<String> carriers : cartMap.values()) {
            uniqueCarriers.addAll(carriers);
        }
        cartCarriers = new ArrayList<>(uniqueCarriers);
    }

    private void countCarriersRanking() {
        for (List<String> carriers: cartMap.values()) {
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
