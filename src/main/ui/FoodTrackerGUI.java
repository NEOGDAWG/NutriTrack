package ui;

import model.Food;
import model.Foodtracker;
import model.EventLog;
import model.Event;

import javax.swing.*;

import static ui.FoodApp.JSON_STORE;

import java.awt.*;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import persistence.JsonWriter;
import persistence.JsonReader;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Represents the GUI for the Food Tracker application. 
// This class is responsible for creating the GUI and handling user interactions.
// Using buttons to add, remove, filter, view, save, and load foods.
public class FoodTrackerGUI extends JFrame {
    private Foodtracker foodTracker;
    private DefaultListModel<String> foodListModel;
    private JList<String> foodList;
    private JLabel feedbackLabel;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final int SPLASH_DURATION = 3000; // 3 seconds

    //MODIFIES: This
    //EFFECTS: constructs a new FoodTrackerGUI with a new Foodtracker, 
    //DefaultListModel, JList, and JsonWriter and JsonReader
    public FoodTrackerGUI() {
        showSplashScreen();
        foodTracker = new Foodtracker();
        foodListModel = new DefaultListModel<>();
        foodList = new JList<>(foodListModel);
        jsonWriter = new JsonWriter(FoodApp.JSON_STORE);
        jsonReader = new JsonReader(FoodApp.JSON_STORE);

        setupGUI();
    }

    //MODIFIES: This
    //EFFECTS: shows the splash screen with the logo of the application for 3 seconds
    private void showSplashScreen() {
        JWindow splash = new JWindow();
        ImageIcon splashIcon = new ImageIcon("./data/LogoApp.jpeg");
        
        Image img = splashIcon.getImage();
        Image scaledImg = img.getScaledInstance(500, 399, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        
        JLabel splashLabel = new JLabel(scaledIcon);
        splash.add(splashLabel);
        splash.pack();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        splash.setLocation(
                (screenSize.width - splash.getWidth()) / 2,
                (screenSize.height - splash.getHeight()) / 2);
        
        splash.setVisible(true);
        
        try {
            Thread.sleep(SPLASH_DURATION);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        splash.dispose();
    }

    //MODIFIES: This
    //EFFECTS: sets up the GUI with a border layout, adds the control panel to the north, 
    //the food panel to the center, and the feedback label to the south
    private void setupGUI() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        add(createControlPanel(), BorderLayout.NORTH);
        add(createFoodPanel(), BorderLayout.CENTER);
        feedbackLabel = new JLabel("Welcome to the Food Tracker!");
        add(feedbackLabel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Event Log:" + "\n");
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.toString() + "\n");
                }
                System.exit(0);
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    //MODIFIES: This
    //EFFECTS: creates a control panel with buttons for adding, viewing, filtering, removing, 
    //setting goal, loading, and saving food data
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        JButton addButton = new JButton("Add Food");
        JButton viewAllButton = new JButton("View All Food & Goal");
        JButton filterButton = new JButton("Show Healthy Foods");
        JButton removeButton = new JButton("Remove Food");
        JButton setGoalButton = new JButton("Set Calorie Goal");
        JButton loadButton = new JButton("Load Data");
        JButton saveButton = new JButton("Save Data");


        addButton.addActionListener(e -> addFood());
        viewAllButton.addActionListener(e -> viewAllFoodAndGoal());
        filterButton.addActionListener(e -> filterHealthyFoods());
        removeButton.addActionListener(e -> removeFood());
        setGoalButton.addActionListener(e -> setCalorieGoal());
        loadButton.addActionListener(e -> loadData());
        saveButton.addActionListener(e -> saveData());


        controlPanel.add(addButton);
        controlPanel.add(viewAllButton);
        controlPanel.add(filterButton);
        controlPanel.add(removeButton);
        controlPanel.add(setGoalButton);
        controlPanel.add(loadButton);
        controlPanel.add(saveButton);

        return controlPanel;
    }

    //MODIFIES: This
    //EFFECTS: creates a food panel with a scroll pane and a titled border for the food list
    private JScrollPane createFoodPanel() {
        JScrollPane scrollPane = new JScrollPane(foodList);
        foodList.setBorder(BorderFactory.createTitledBorder("Eaten Today"));
        return scrollPane;
    }

    //MODIFIES: This
    //EFFECTS: adds a new food to the food tracker and the food list
    // Also Displays the food added successfully
    private void addFood() {
        String name = JOptionPane.showInputDialog(this, "Enter food name:");
        String calorieStr = JOptionPane.showInputDialog(this, "Enter food calories:");
        String healthyStr = JOptionPane.showInputDialog(this, "Is the food healthy? (true/false):");

        try {
            double calories = Double.parseDouble(calorieStr);
            boolean isHealthy = Boolean.parseBoolean(healthyStr);

            Food food = new Food(name, calories, isHealthy);
            foodTracker.addFood(food);
            foodListModel.addElement(name + " - " + calories + " cal (Healthy: " + isHealthy + ")");
            feedbackLabel.setText("Food added successfully!");
        } catch (Exception e) {
            feedbackLabel.setText("Invalid input. Please try again.");
        }
    }

    //MODIFIES: This
    //EFFECTS: clears the food list, adds all foods from the food tracker to the list, 
    //and displays the calorie goal
    private void viewAllFoodAndGoal() {
        foodListModel.clear();

        foodListModel.addElement("=== DAILY CALORIE GOAL: " + foodTracker.getCalorieGoal() + " CALORIES ===");
        foodListModel.addElement(""); // Empty line for spacing
        

        for (Food food : foodTracker.viewFoodIntake()) {
            foodListModel.addElement(food.getFoodName() + " - " 
                    + food.getCalories() + " cal (Healthy: " + food.isHealthy() + ")");
        }
        feedbackLabel.setText("Displaying all foods and calorie goal.");
    }

    //MODIFIES: This
    //EFFECTS: clears the food list, adds all healthy foods from the food tracker to the list, 
    //and displays the healthy foods
    private void filterHealthyFoods() {
        foodListModel.clear();
        for (Food food : foodTracker.filterHealthyFoods()) {
            foodListModel.addElement(food.getFoodName() + " - " + food.getCalories() + " cal (Healthy)");
        }
        feedbackLabel.setText("Displaying only healthy foods.");
    }

    //MODIFIES: This
    //EFFECTS: removes a food from the food tracker and the food list
    // Also Displays the food remaining after removal successfully
    private void removeFood() {
        String name = JOptionPane.showInputDialog(this, "Enter the name of the food you want to remove:");
        foodTracker.deleteFood(name);
        viewAllFoodAndGoal(); // Refresh the list after removal
        feedbackLabel.setText(name + " has been removed (if it existed).");
    }

    //MODIFIES: This
    //EFFECTS: sets the calorie goal for the food tracker
    private void setCalorieGoal() {
        String goalStr = JOptionPane.showInputDialog(this, "Enter your calorie goal:");

        try {
            double goal = Double.parseDouble(goalStr);
            foodTracker.setCalorieGoal(goal);
            feedbackLabel.setText("Calorie goal set to: " + goal);
        } catch (Exception e) {
            feedbackLabel.setText("Invalid input. Please try again.");
        }
    }

    //MODIFIES: This
    //EFFECTS: loads data from file and displays it to the user
    private void loadData() {
        loadThisFoodTracker();
        feedbackLabel.setText("Data loaded from file.");
    }

    //MODIFIES: This
    //EFFECTS: saves data to file and stores it
    private void saveData() {
        saveThisFoodTracker();
        feedbackLabel.setText("Data saved to file.");
    }

     //EFFECTS: Saves data to Json file
    public void saveThisFoodTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.foodTracker);
            jsonWriter.close();

        } catch (FileNotFoundException e) {
            feedbackLabel.setText("Invalid input. Please try again.");
        }

    }

    //EFFECTS: loads data from file and refreshes the food display
    public void loadThisFoodTracker() {
        try {
            this.foodTracker = jsonReader.read();
            System.out.println("Your file has been loaded");
        } catch (Exception e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        viewAllFoodAndGoal();

    }
}

