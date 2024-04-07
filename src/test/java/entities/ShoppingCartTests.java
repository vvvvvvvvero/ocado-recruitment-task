package entities;

import org.example.entities.ShoppingCart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ShoppingCartTests {

    @Test
    void testConstructorThrowsExceptionForNullProducts() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ShoppingCart(null));
        assertEquals("List of products cannot be null or empty", exception.getMessage());
    }

    @Test
    void testConstructorThrowsExceptionForEmptyProducts() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ShoppingCart(Collections.emptyList()));
        assertEquals("List of products cannot be null or empty", exception.getMessage());
    }

    @Test
    void testPrepareCartSuccessfullyPopulates() {
        ShoppingCart cart = new ShoppingCart(List.of("Cookies Oatmeal Raisin", "Cheese Cloth"));
        Map<String, List<String>> productToCarriers = Map.of(
                "Cookies Oatmeal Raisin", List.of("Pick-up point", "Parcel locker"),
                "Cheese Cloth", List.of("Courier", "Parcel locker", "Same day delivery", "Next day shipping")
        );

        cart.prepareCart(productToCarriers);

        assertEquals(2, cart.getNumberOfProducts());
        assertEquals(5, cart.getNumberOfCarriers());
        assertEquals(2, (int) cart.getCarriersRank().get("Parcel locker"));
    }

    @Test
    void testPrepareCartThrowsExceptionForUnmappedProduct() {
        List<String> productsInCart = List.of("Cookies Oatmeal Raisin", "Cheese Cloth");
        ShoppingCart cart = new ShoppingCart(productsInCart);

        Map<String, List<String>> productsToCarriersMap = new HashMap<>();
        productsToCarriersMap.put("Cookies Oatmeal Raisin", List.of("Pick-up point", "Parcel locker"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.prepareCart(productsToCarriersMap);
        });

        assertTrue(exception.getMessage().contains("Cheese Cloth"), "Exception message should mention the unmapped product.");

    }


}
