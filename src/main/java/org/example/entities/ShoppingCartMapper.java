package org.example.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class ShoppingCartMapper {
    ShoppingCart shoppingCart;

    public Map<Integer, String> getIndexedProducts() {
        Map<Integer, String> indexedProducts = new HashMap<>();
        for (int i = 0; i < shoppingCart.getNumberOfProducts(); i++) {
            indexedProducts.put(i, shoppingCart.getCartProducts().get(i));
        }
        return indexedProducts;
    }

    public Map<Integer, String> getIndexedCarriers() {
        Map<Integer, String> indexedCarriers = new HashMap<>();
        for (int i = 0; i < shoppingCart.getNumberOfCarriers(); i++) {
            indexedCarriers.put(i, shoppingCart.getCartCarriers().get(i));
        }
        return indexedCarriers;
    }

    public Map<Integer, Integer> getCarrierIndexToTheirRankMap() {
        Map<Integer, Integer> carriersRanking = new HashMap<>();
        for (int i = 0; i < shoppingCart.getNumberOfCarriers(); i++) {
            carriersRanking.put(i, shoppingCart.getCarriersRank().get(shoppingCart.getCartCarriers().get(i)));
        }
        return carriersRanking;
    }

    public Map<Integer, Set<Integer>> getProductIndexToTheirCarrierMap() {
        Map<Integer, Set<Integer>> productToCarrierMap = new HashMap<>();
        for (int i = 0; i < shoppingCart.getNumberOfProducts(); i++) {
            Set<Integer> carriers = new HashSet<>();
            for (String carrier : shoppingCart.getCartMap().get(shoppingCart.getCartProducts().get(i))) {
                carriers.add(shoppingCart.getCartCarriers().indexOf(carrier));
            }
            productToCarrierMap.put(i, carriers);
        }
        return productToCarrierMap;
    }
}
