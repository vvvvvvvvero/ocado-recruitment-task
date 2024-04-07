package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class CartService {
    List<String> cartProducts;
    List<String> cartCarriers;
    Map<String, List<String>> cartMap = new HashMap<>();
    DataService dataService;


    public CartService(DataService dataService, List<String> cartContents) {
        this.dataService = dataService;
        dataService.loadData();
        dataService.countCarriersRanking();
        this.cartProducts = new ArrayList<>(cartContents);
    }

    public void createCartMap() {
        for (String product : cartProducts) {
            cartMap.put(product, dataService.getProductsToCarriersMap().get(product));
        }
        cartCarriers = new ArrayList<>(cartMap.values().stream().flatMap(Collection::stream).distinct().toList());
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
            carriersRanking.put(i, dataService.getCarriersRankingMap().get(cartCarriers.get(i)));
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
        List<String> cartContents = List.of("Cookies Oatmeal Raisin", "Cheese Cloth", "Ecolab - Medallion");
        CartService cartManager = new CartService(dataService, cartContents);
        cartManager.createCartMap();
        System.out.println("Cart Map: " + cartManager.getCartMap());
        System.out.println("Indexed Carriers: " + cartManager.getIndexedCarriers());
        System.out.println("Indexed Products: " + cartManager.getIndexedProducts());
        System.out.println("Carrier and Their Rank: " +cartManager.getIndexedCarriersRanking());
        System.out.println("Products and Allowed Carriers: " + cartManager.getIndexedProductsToAllowedCarriers());
    }

}
