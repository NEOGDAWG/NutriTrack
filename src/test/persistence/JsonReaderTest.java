package persistence;

import model.Food;
import model.Foodtracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {
    private JsonReader reader;

    @BeforeEach
    void setup() {
        // Initialize the JsonReader with the path to the test JSON file
        reader = new JsonReader("./data/testValidFile.json");
    }

    @Test
    void testReadValidFile() {
        try {
            Foodtracker tracker = reader.read();
            assertNotNull(tracker);

            // Test calorie goal
            assertEquals(2000, tracker.getCalorieGoal());

            // Test food items in "eatenToday"
            assertEquals(2, tracker.viewFoodIntake().size());

            // Check the first food item
            Food food1 = tracker.viewFoodIntake().get(0);
            assertEquals("Apple", food1.getFoodName());
            assertEquals(95, food1.getCalories());
            assertTrue(food1.isHealthy());

            // Check the second food item
            Food food2 = tracker.viewFoodIntake().get(1);
            assertEquals("Burger", food2.getFoodName());
            assertEquals(354, food2.getCalories());
            assertFalse(food2.isHealthy());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testReadFileNotFound() {
        JsonReader readerWithInvalidPath = new JsonReader("./data/nonExistentFile.json");
        assertThrows(IOException.class, readerWithInvalidPath::read);
    }

    @Test
    void testReadEmptyFile() {
        JsonReader readerEmptyFile = new JsonReader("./data/testEmptyFile.json");
        assertThrows(org.json.JSONException.class, readerEmptyFile::read);
    }


}
