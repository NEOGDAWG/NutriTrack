package persistence;

import model.Food;
import model.Foodtracker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads Foodtracker data from JSON file
public class JsonReader {
    private String source;

    // EFFECTS: Constructs reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads Foodtracker from file and returns it
    // throws IOException if any problems reading the file
    public Foodtracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFoodtracker(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Foodtracker from JSON object and returns it
    private Foodtracker parseFoodtracker(JSONObject jsonObject) {
        Foodtracker tracker = new Foodtracker();
        addCalorieGoal(tracker, jsonObject);
        addFoodItems(tracker, jsonObject);
        return tracker;
    }

    // MODIFIES: Foodtracker
    // EFFECTS: parses calorie goal from JSON object and sets it in Foodtracker
    private void addCalorieGoal(Foodtracker tracker, JSONObject jsonObject) {
        int calorieGoal = jsonObject.getInt("calorieGoal");
        tracker.setCalorieGoal(calorieGoal);
    }

    // MODIFIES: Foodtracker
    // EFFECTS: parses eatenToday from JSON object and adds each item to Foodtracker
    private void addFoodItems(Foodtracker tracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("eatenToday");
        for (Object item : jsonArray) {
            JSONObject foodObject = (JSONObject) item;
            addFood(tracker, foodObject);
        }
    }

    // MODIFIES: Foodtracker
    // EFFECTS: parses a food item from JSON object and adds it to Foodtracker
    private void addFood(Foodtracker tracker, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int calories = jsonObject.getInt("calories");
        boolean ishealthy = jsonObject.getBoolean("ishealthy");
        Food food = new Food(name, calories, ishealthy);
        tracker.addFood(food);
    }
}