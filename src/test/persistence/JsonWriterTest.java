package persistence;

import model.Food;
import model.Foodtracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private static final String TEST_FILE = "./data/testWriter.json";
    private JsonWriter writer;
    private Foodtracker tracker;

    @BeforeEach
    void setup() {
        tracker = new Foodtracker();
        writer = new JsonWriter(TEST_FILE);
    }


    @Test
    void testWriteEmptyFoodtracker() {
        try {
            writer.open();
            writer.write(tracker);
            writer.close();

            // Read back the file to ensure the content was written correctly
            JsonReader reader = new JsonReader(TEST_FILE);
            Foodtracker loadedTracker = reader.read();
            assertEquals(0, loadedTracker.getCalorieGoal());
            assertTrue(loadedTracker.viewFoodIntake().isEmpty());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriteFoodtrackerWithContent() {
        try {
            // Set up a tracker with some content
            tracker.setCalorieGoal(2000);
            tracker.addFood(new Food("Apple", 95, true));
            tracker.addFood(new Food("Burger", 500, false));
            writer.open();
            writer.write(tracker);
            writer.close();
            JsonReader reader = new JsonReader(TEST_FILE);
            Foodtracker loadedTracker = reader.read();
            assertEquals(2000, loadedTracker.getCalorieGoal());
            assertEquals(2, loadedTracker.viewFoodIntake().size());

            Food food1 = loadedTracker.viewFoodIntake().get(0);
            assertEquals("Apple", food1.getFoodName());
            assertEquals(95, food1.getCalories());
            Food food2 = loadedTracker.viewFoodIntake().get(1);
            assertEquals("Burger", food2.getFoodName());
            assertEquals(500, food2.getCalories());
            assertFalse(food2.isHealthy());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

}
