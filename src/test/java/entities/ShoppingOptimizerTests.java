package entities;

import org.example.entities.ShoppingCartOptimizer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingOptimizerTests {

    @Test
    void testBaseCase() {
        Map<Integer, String> products = Map.of(
                0, "Carrots (1kg)",
                1, "Cold Beer (330ml)",
                2, "Steak (300g)",
                3, "AA Battery (4 Pcs.)",
                4, "Espresso Machine",
                5, "Garden Chair"
        );

        Map<Integer, String> carriers = Map.of(
                0, "Express Delivery",
                1, "Click&Collect",
                2, "Courier"
        );

        ShoppingCartOptimizer optimizer = new ShoppingCartOptimizer(
                6,
                3,
                Map.of(
                        0, Set.of(0, 1),
                        1, Set.of(0),
                        2, Set.of(0, 1),
                        3, Set.of(0, 2),
                        4, Set.of(1, 2),
                        5, Set.of(2)
                ),
                Map.of(0, 40, 1, 30, 2, 30)
        );

        Map<String, List<String>> result = optimizer.createSolutionMap(products, carriers);

        assertEquals("Express Delivery", result.get("Carrots (1kg)").getFirst(), "Carrots should be assigned to Express Delivery");
        assertEquals("Express Delivery", result.get("Cold Beer (330ml)").getFirst(), "Cold Beer should be assigned to Express Delivery");
        assertEquals("Express Delivery", result.get("Steak (300g)").getFirst(), "Steak should be assigned to Express Delivery");
        assertEquals("Express Delivery", result.get("AA Battery (4 Pcs.)").getFirst(), "AA Battery should be assigned to Express Delivery");
        assertEquals("Courier", result.get("Espresso Machine").getFirst(), "Espresso Machine should be assigned to Click&Collect");
        assertEquals("Courier", result.get("Garden Chair").getFirst(), "Garden Chair should be assigned to Courier");

    }

    @Test
    void testScoreDecisionFirst() {
        Map<Integer, String> products = Map.of(
                0, "Cookies Oatmeal Raisin",
                1, "Cheese Cloth",
                2, "English Muffin"
        );
        Map<Integer, String> carriers = Map.of(
                0, "Pick-up point",
                1, "Parcel locker"
        );
        ShoppingCartOptimizer optimizer = new ShoppingCartOptimizer(
                3,
                2,
                Map.of( // allowed carriers for each product
                        0, Set.of(0), // Cookies can be picked up
                        1, Set.of(1), // Cheese must be in a parcel locker
                        2, Set.of(0, 1) // Muffin can be picked up or in a parcel locker
                ),
                Map.of(0, 10, 1, 20) // Second Carrier Score is higher so the second carrier should be chosen for Muffin
        );

        Map<String, List<String>> result = optimizer.createSolutionMap(products, carriers);

        assertEquals("Pick-up point", result.get("Cookies Oatmeal Raisin").getFirst(), "Cookies should be assigned to Pick-up point");
        assertEquals("Parcel locker", result.get("Cheese Cloth").getFirst(), "Cheese should be assigned to Parcel locker");
        assertEquals("Parcel locker", result.get("English Muffin").getFirst(), "English Muffin should be assigned to Parcel locker");
    }

    @Test
    void testScoreDecisionSecond() {
        // Setup
        Map<Integer, String> products = Map.of(
                0, "Cookies Oatmeal Raisin",
                1, "Cheese Cloth",
                2, "English Muffin"
        );
        Map<Integer, String> carriers = Map.of(
                0, "Pick-up point",
                1, "Parcel locker"
        );
        ShoppingCartOptimizer optimizer = new ShoppingCartOptimizer(
                3,
                2,
                Map.of(
                        0, Set.of(0),
                        1, Set.of(1),
                        2, Set.of(0, 1) // Muffin can be picked up or in a parcel locker
                ),
                Map.of(0, 30, 1, 20) // First Carrier Score is higher so the second carrier should be chosen for Muffin
        );

        Map<String, List<String>> result = optimizer.createSolutionMap(products, carriers);

        assertEquals("Pick-up point", result.get("Cookies Oatmeal Raisin").getFirst(), "Cookies should be assigned to Pick-up point");
        assertEquals("Parcel locker", result.get("Cheese Cloth").getFirst(), "Cheese should be assigned to Parcel locker");
        assertEquals("Pick-up point", result.get("English Muffin").getFirst(), "English Muffin should be assigned to Pick-up point");
    }


}
