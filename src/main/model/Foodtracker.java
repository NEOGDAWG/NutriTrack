package model;

import java.util.ArrayList;
import java.util.List;
// Represents the food tracker, which keeps track of the food
// the user has entered along with the calorie goal. Also
// includes methods to alter the list keeping track of the food. 

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

public class Foodtracker implements Writable {
    private List<Food> eatenToday; //list of food eaten
    private double calorieGoal; // calorie goal set by user


    /*
     * MODFIES: This
     * EFFECTS: Constructs an empty list for eaten foods and 
     * intializes the calorie goal as 0
     */
    public Foodtracker() {
        eatenToday = new ArrayList<Food>();
        calorieGoal = 0;
    }

    // MODIFIES: This
    // EFFECTS: adds a given food to the list of food eaten.
    public void addFood(Food newfood) {
        eatenToday.add(newfood);
        String message = "New food added to Food tracker:" + " " + newfood.getFoodName();
        EventLog.getInstance().logEvent(new Event(message));
    }

    // REQUIRES: Food name must be a food in the eatentoday list
    // MODIFIES: This
    // EFFECTS: removes a specific food name from the list
    public void deleteFood(String name) {
        for (int i = 0; i < eatenToday.size(); i++) {
            if (eatenToday.get(i).getFoodName().equals(name)) {
                eatenToday.remove(i);
                i--;
            }
        }
        String message = "Food removed from food tracker:" + " " + name;
        EventLog.getInstance().logEvent(new Event(message));

    }
    //REQUIRES: goal > 0
    //MODIFIES: This
    //EFFECTS: sets the calorie goal

    public void setCalorieGoal(double goal) {
        calorieGoal = goal;
        String message = "Calorie goal was set to" + " " + goal;
        EventLog.getInstance().logEvent(new Event(message));

    }

    // returns a list of all the food items eaten today
    public List<Food> viewFoodIntake() {
        return eatenToday;
        
    }

    //returns the calorie goal
    public double getCalorieGoal() {
        return calorieGoal;
    }

    @Override
    //EFFECTS: return calorie goal and eatentoday as Json Objects
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("calorieGoal", calorieGoal);
        json.put("eatenToday", eatenTodayToJson());
        return json;
    }

    // EFFECTS: returns eatentoday list in this foodtracker as a JSON array
    private JSONArray eatenTodayToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Food f : eatenToday) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns a list of only healthy foods from eatenToday
    public List<Food> filterHealthyFoods() {
        List<Food> healthyFoods = new ArrayList<>();
        for (Food food : eatenToday) {
            if (food.isHealthy()) {
                healthyFoods.add(food);
            }
        }
        String message = "filtered out all unhealthy foods";
        EventLog.getInstance().logEvent(new Event(message));
        return healthyFoods;
    }
    
}
