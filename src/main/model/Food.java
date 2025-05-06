package model;

import org.json.JSONObject;

import persistence.Writable;

// represents a given food having a name, calories in the eaten porsion, 
// and if the food is healthy
public class Food implements Writable {
    private double calories; //calories in food
    private String name;     // name of food
    private boolean ishealthy; // True if the food is healthy, False if not.

/*
 * REQUIRES: name of food has a non-zero length, and calories
 * is not negative
 * MODIFIES: This
 * EFFECTS: initializes and constructs the object Food.
 * the name of the food is set to the given name. 
 * The calories is set to the given caloriesInPortion, and
 * the ishealthy variable is set to the given truth value of healthy.
 */

    public Food(String foodname, double caloriesInPortion, boolean healthy) {
        name = foodname;
        calories = caloriesInPortion;
        ishealthy = healthy;

    }

    public double getCalories() {    //gets calories of food
        return calories;
    }

    public String getFoodName() {   //gets the name of food
        return name;
        
    }

    public boolean isHealthy() {  //gets truth value of ishealthy
        return ishealthy;
        
    }

    @Override
    //EFFECTS: Returns Food's name, claories, and ishealthy as Json Objects
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("calories", calories);
        json.put("ishealthy", ishealthy);
        return json;
    }



}
