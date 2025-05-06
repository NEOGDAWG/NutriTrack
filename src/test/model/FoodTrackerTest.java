package model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class FoodTrackerTest {
    private Foodtracker testFoodTracker;
    private Food testFoodOne;
    private Food testFoodTwo;

    @BeforeEach
    void runBefore() {
        testFoodTracker = new Foodtracker();
        testFoodOne = new Food("apple", 95.00, true);
        testFoodTwo = new Food("Mcchicken", 105.00,false);

    }

    @Test
    void testConstructer() {
        assertTrue(testFoodTracker.viewFoodIntake().isEmpty());
        assertEquals(0, testFoodTracker.getCalorieGoal());
    }

    @Test
    void testAddFood() {
        testFoodTracker.addFood(testFoodOne);
        testFoodTracker.addFood(testFoodTwo);
        assertEquals(2, testFoodTracker.viewFoodIntake().size());
        assertEquals("apple",testFoodTracker.viewFoodIntake().get(0).getFoodName());
        assertEquals(95.00, testFoodTracker.viewFoodIntake().get(0).getCalories());
        assertTrue(testFoodTracker.viewFoodIntake().get(0).isHealthy());

        assertEquals("Mcchicken", testFoodTracker.viewFoodIntake().get(1).getFoodName());
        assertEquals(105.00, testFoodTracker.viewFoodIntake().get(1).getCalories());
        assertFalse(testFoodTracker.viewFoodIntake().get(1).isHealthy());

    }

    @Test
    void testDeleteFood() {
        testFoodTracker.addFood(testFoodOne);
        testFoodTracker.addFood(testFoodTwo);
        testFoodTracker.addFood(testFoodTwo);
        testFoodTracker.deleteFood("orange");
        assertEquals(3, testFoodTracker.viewFoodIntake().size());
        testFoodTracker.deleteFood("apple");
        assertEquals("Mcchicken",testFoodTracker.viewFoodIntake().get(0).getFoodName());
        assertEquals(2, testFoodTracker.viewFoodIntake().size());
        testFoodTracker.deleteFood("Mcchicken");
        assertEquals(0, testFoodTracker.viewFoodIntake().size());
        assertTrue(testFoodTracker.viewFoodIntake().isEmpty());

    }

    @Test
    void testSetGoal() {
        testFoodTracker.setCalorieGoal(500);
        assertEquals(500, testFoodTracker.getCalorieGoal());
    }

    @Test
    void testFilterHealthyFoods() {
        testFoodTracker.addFood(testFoodOne);  // healthy food
        testFoodTracker.addFood(testFoodTwo);  // unhealthy food
        List<Food> healthyFoods = testFoodTracker.filterHealthyFoods();
        assertEquals(1, healthyFoods.size());
        assertEquals("apple", healthyFoods.get(0).getFoodName());
        assertTrue(healthyFoods.get(0).isHealthy());
    }

}
