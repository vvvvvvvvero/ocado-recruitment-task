package entities;

import org.example.entities.ShoppingCart;
import org.example.entities.ShoppingCartMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

public class ShoppingCartMapperTests {
    ShoppingCart shoppingCart;
    ShoppingCartMapper shoppingCartMapper;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart(List.of("Cookies Oatmeal Raisin", "Cheese Cloth", "Ecolab - Medallion"));
        shoppingCart.prepareCart(Map.of(
                "Cookies Oatmeal Raisin", List.of("Pick-up point", "Parcel locker", "Courier"),
                "Cheese Cloth", List.of("Courier", "Same day delivery"),
                "Ecolab - Medallion", List.of("Pick-up point", "Same day delivery")
        ));
        shoppingCartMapper = new ShoppingCartMapper(shoppingCart);
    }

    @Test
    void testGetIndexedProducts() {
        Map<Integer, String> indexedProducts = shoppingCartMapper.getIndexedProducts();
        assertEquals(3, indexedProducts.size());
        assertEquals("Cookies Oatmeal Raisin", indexedProducts.get(0));
        assertEquals("Cheese Cloth", indexedProducts.get(1));
        assertEquals("Ecolab - Medallion", indexedProducts.get(2));
    }

    @Test
    void testGetIndexedCarriers() {
        Map<Integer, String> indexedCarriers = shoppingCartMapper.getIndexedCarriers();
        assertEquals(4, indexedCarriers.size());
        assertEquals("Pick-up point", indexedCarriers.get(0));
        assertEquals("Parcel locker", indexedCarriers.get(1));
        assertEquals("Same day delivery", indexedCarriers.get(2));
        assertEquals("Courier", indexedCarriers.get(3));
    }

    @Test
    void testGetCarrierIndexToTheirRankMap() {
        Map<Integer, Integer> carrierIndexToRank = shoppingCartMapper.getCarrierIndexToTheirRankMap();
        assertEquals(4, carrierIndexToRank.size());
        assertEquals(2, (int) carrierIndexToRank.get(0));
        assertEquals(1, (int) carrierIndexToRank.get(1));
        assertEquals(2, (int) carrierIndexToRank.get(2));
        assertEquals(2, (int) carrierIndexToRank.get(3));
    }


}
