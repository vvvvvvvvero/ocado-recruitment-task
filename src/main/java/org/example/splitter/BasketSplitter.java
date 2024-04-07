package org.example.splitter;

import lombok.Getter;
import lombok.Setter;
import org.example.entities.ShoppingCart;
import org.example.entities.ShoppingCartMapper;
import org.example.entities.ShoppingCartOptimizer;
import org.example.util.FileService;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class BasketSplitter {

    private final String absolutePathToConfigFile;
    private final FileService fileService;

    public BasketSplitter(String absolutePathToConfigFile) {
        this.absolutePathToConfigFile = absolutePathToConfigFile;
        this.fileService = new FileService();
    }

    public Map<String, List<String>> split(List<String> items) {
        Map<String, List<String>> productsToCarriersMap = fileService.readConfigFile("/Users/veraemelianova/IdeaProjects/ocado-task/src/main/resources/config.json");

        ShoppingCart shoppingCart = new ShoppingCart(items);
        shoppingCart.prepareCart(productsToCarriersMap);

        ShoppingCartMapper shoppingCartMapper = new ShoppingCartMapper(shoppingCart);

        int numberOfProducts = shoppingCart.getNumberOfProducts();
        int numberOfCarriers = shoppingCart.getNumberOfCarriers();
        Map<Integer, Set<Integer>> availableCarriers = shoppingCartMapper.getProductIndexToTheirCarrierMap();
        Map<Integer, Integer> carrierRanks = shoppingCartMapper.getCarrierIndexToTheirRankMap();

        ShoppingCartOptimizer shoppingCartOptimizer = new ShoppingCartOptimizer(numberOfProducts, numberOfCarriers, availableCarriers, carrierRanks);
        return shoppingCartOptimizer.createSolutionMap(shoppingCartMapper.getIndexedProducts(), shoppingCartMapper.getIndexedCarriers());

    }

    public static void main(String[] args) {
        BasketSplitter basketSplitter = new BasketSplitter("src/main/resources/config.json");
        List<String> products = basketSplitter.getFileService().readCartFile("/Users/veraemelianova/IdeaProjects/ocado-task/src/main/resources/basket-1.json");
        System.out.println(basketSplitter.split(products));

    }
}
