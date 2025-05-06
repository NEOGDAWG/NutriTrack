package ui;

import model.Food;
import model.Foodtracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


// Food Tracker application, handles all user input
// and user interface

public class FoodApp {
    public static final String JSON_STORE = "./data/foodtracker.json";
    private Foodtracker foodTracker;
    private Scanner scanner; // Scanner for user input
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: This
    // EFFECTS: initializes the foodtracker and scanner for user input and
    // calls the run method to start the application
    public FoodApp() throws FileNotFoundException {
        foodTracker = new Foodtracker(); // Initialize Foodtracker
        scanner = new Scanner(System.in); // Initialize Scanner
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        run(); // Start the application
    }

    // MODIFIES: This
    // EFFECTS: Sets running to true; when true, the application runs
    private void run() {
        boolean running = true;

        while (running) {
            printMenu(); // Display the menu options
            String choice = scanner.nextLine();
            running = handleUserChoice(choice); // Process the user choice
        }
    }

    //EFFECTS: Displays the menue
    private void printMenu() {
        System.out.println("\nPlease select an option:");
        System.out.println("1. Add a food to today's list");
        System.out.println("2. Set a calorie goal");
        System.out.println("3. View today's food and calorie goal");
        System.out.println("4. Remove a food from today's list");
        System.out.println("5. Exit");
        System.out.println("6. Save Items");
        System.out.println("7. Load previously saved Items from file");
        System.out.print("Enter your choice: ");
    }

    // MODIFIES: This
    // EFFECTS: Calls on the method which coresponds with the user choice
    private boolean handleUserChoice(String choice) {
        switch (choice) {
            case "1":
                addFood(); // Add a food item
                break;
            case "2":
                setCalorieGoal(); // Set the calorie goal
                break;
            case "3":
                viewFoodAndGoal(); // View food intake and calorie goal
                break;
            case "4":
                removeFood(); // Remove a food item
                break;
            case "5":
                return false;
            case "6":
                saveFoodTracker();
                break;
            case "7":
                loadFoodTracker();
                break;
        }
        return true;
    }

    // REQUIRES: Name must have a non-zero length
    // Calories must be >= 0, the user input for ishealthy
    // should be either "true" or "false."
    // MODIFIES: modifies the eatentoday list in foodtracker
    // EFFECTS: adds a given food to the eatentoday list
    private void addFood() {
        System.out.print("Enter the name of the food: ");
        String name = scanner.nextLine();
        System.out.print("Enter the calories of the food: ");
        double calories = Double.parseDouble(scanner.nextLine());
        System.out.print("Is the food healthy? (true/false): ");
        boolean isHealthy = Boolean.parseBoolean(scanner.nextLine());

        Food newFood = new Food(name, calories, isHealthy); // Create a new food object
        foodTracker.addFood(newFood); // Add the food to the tracker
        System.out.println(name + " has been added to your list.");
    }

    // REQUIRES: Calorie goal must be >0
    // MODIFIES: modifies the setCalorieGoal variable in foodtracker
    // EFFECTS: Sets the calorie goal
    private void setCalorieGoal() {
        System.out.print("Enter your calorie goal for the day: ");
        double goal = Double.parseDouble(scanner.nextLine());
        foodTracker.setCalorieGoal(goal);
        System.out.println("Calorie goal set to: " + goal);
    }

    // EFFECTS: prints out a list of foods added to the eatentoday list
    private void viewFoodAndGoal() {
        System.out.println("\nFood eaten today:");
        if (foodTracker.viewFoodIntake().isEmpty()) {
            System.out.println("No foods have been added yet.");
        } else {
            for (Food food : foodTracker.viewFoodIntake()) {
                System.out.println(" - " + food.getFoodName() + ": " + food.getCalories()
                        + " calories (Healthy: " + food.isHealthy() + ")");
            }
        }
        System.out.println("Calorie goal: " + foodTracker.getCalorieGoal());
    }

    // REQUIRES: String name must be the name of a food in the list
    // MODIFIES: This
    // EFFECTS: Removes a given food from the eatentoday list in foodtracker
    private void removeFood() {
        System.out.print("Enter the name of the food you want to remove: ");
        String name = scanner.nextLine();
        foodTracker.deleteFood(name);
        System.out.println(name + " has been removed from the list (if it existed).");
    }

    //EFFECTS: Saves data to file
    private void saveFoodTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.foodTracker);
            jsonWriter.close();
            System.out.println("complete!");

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }

    }

    //MODIFIES: This
    //EFFECTS: Loads data from file
    private void loadFoodTracker() {
        try {
            this.foodTracker = jsonReader.read();
            System.out.println("Your file has been loaded");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

    }
    
}

