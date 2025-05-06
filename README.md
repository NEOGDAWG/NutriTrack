# NUTRITRACK

## An application that tracks your food intake and eating habits



This project is a **Food Tracker** application that allows users to easily log their daily food intake and track the calories they consume. Designed for anyone aiming to manage their diet, moniter their eating habits, lose weight, or *maintain a healthier lifestyle*, the app provides a simple interface to input meals and monitor your progress over time. This project interests me because I am passionate about my health. One thing I have noticed is that I am not good with my eating habits and mostly eat whatever I want whenever I want. I found that making a food tracker that helps me manage my calories and moniter my eating habits would be a great way to maintain a healther lifestyle.



**Some other features the application will be able to perform:**
- Allow the user to input food and calories eaten on a specific day.

- Be able to view past food intakes and moniter progress over time.

- Set goals for calories to be consumed by the end of a given day.

## **User Stories**
 - As a user, I want to be able to add a food to my eatentoday list and specify the name, calories, and if that food is "healthy" or not

 - As a user, I want to be able to view my food intake of a specific day.

 - As a user, I want to be able to set goals for calories to be consumed.

 - As a user, I want to be able to delete certain foods from my eatentoday list.

 - As a user, I want to be able to be able to save my FoodApp calories tracker, saving all
 the foods, calories, and personal goal that has been set. (only If I chooose to)

 - As a user, I want to be able to load the FoodApp tracker that I have saved at a previous point(only if I choose to).

 - As a user, I want to be able to filter the foods that I have eaten based on if they are healthy.



**Phase 4: Task 2:**

Event Log:

Thu Nov 28 19:21:57 PST 2024
New food added to Food tracker: apple

Thu Nov 28 19:22:05 PST 2024
New food added to Food tracker: burger

Thu Nov 28 19:22:17 PST 2024
Calorie goal was set to 5000.0

Thu Nov 28 19:22:21 PST 2024
filtered out all unhealthy foods

Thu Nov 28 19:22:25 PST 2024
Food removed from food tracker: apple



**Phase 4: Task 3:**

If I had more time to work on the project, I would extract common functionality from FoodTrackerGUI and FoodApp into a shared abstract class or interface to reduce code duplication, particularly in the save and load operations. This is because the foodApp and FoodTrackerGUI class have a some code duplication that can be extracted to another make the code more easy to read and reduce duplication, also making it easier to test. Additionally, I would refactor the Food and Foodtracker classes to better handle data validation and error handling. Currently, many methods rely on REQUIRES clauses in their documentation rather than implementing proper input validation. I would introduce exception handling for invalid inputs (like negative calories or empty food names) and create custom exceptions for these cases.





