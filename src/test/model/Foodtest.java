package model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class Foodtest {
    private Food testFood;

    @BeforeEach
    void runBefore() {
        boolean health = true;
        testFood = new Food("apple", 95.00, health);
    }

    @Test
    void testConstructer() {
        assertEquals("apple", testFood.getFoodName());
        assertEquals(95.00, testFood.getCalories());
        assertTrue(testFood.isHealthy());
    }

}
