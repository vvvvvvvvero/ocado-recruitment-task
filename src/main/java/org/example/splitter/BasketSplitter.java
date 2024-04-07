package org.example.splitter;

import org.example.CartService;
import org.example.DataService;
import org.example.Main;

import java.util.List;
import java.util.Map;

public class BasketSplitter {

    private final String absolutePathToConfigFile;

    public BasketSplitter(String absolutePathToConfigFile) {
        this.absolutePathToConfigFile = absolutePathToConfigFile;
    }

    public Map<String, List<String>> split(List<String> items) {
        DataService dataService = new DataService(absolutePathToConfigFile);
        CartService cartService = new CartService(dataService);
        cartService.loadProductsFromFile("/Users/veraemelianova/IdeaProjects/ocado-task/src/main/resources/basket-1.json");
        cartService.createCartMap();
        cartService.countCarriersRanking();
        var carriers = cartService.getIndexedCarriers();
        var products = cartService.getIndexedProducts();
        var carriersRanking = cartService.getIndexedCarriersRanking();
        var allowed = cartService.getIndexedProductsToAllowedCarriers();
        var result = Main.minimizeColumnsWithOnes(cartService.getNumberOfProducts(), cartService.getNumberOfCarriers(), allowed, carriersRanking);
        return Main.getResultMap(result, products,carriers);
    }

    public static void main(String[] args) {
        BasketSplitter basketSplitter = new BasketSplitter("src/main/resources/config.json");
        System.out.println(basketSplitter.split(List.of("Cookies Oatmeal Raisin", "Cheese Cloth")));
    }
}
