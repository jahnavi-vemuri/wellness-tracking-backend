package edu.indiana.se2.Wellness.Tracker.services.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class LocalChatBotService {

    @Autowired
    private DataService dataService;

    private final Random random = new Random();

    public String getResponse(String message) {
        String lowerMessage = message.toLowerCase();

        // Diet related queries
        if (lowerMessage.contains("diet") || lowerMessage.contains("food") || lowerMessage.contains("recipe") || lowerMessage.contains("meal")) {
            return getFilteredDietRecommendation(lowerMessage);
        }

        if (lowerMessage.contains("age") && lowerMessage.contains("gender")) {
            return getHealthMetricsByAgeAndGender(lowerMessage);
        }

        // If user types the exact exercise name after seeing list
        if (dataService.getExerciseDetailsByName(message.trim()) != null) {
            return getExerciseDetailsByName(message.trim());
        }

        // Exercise related queries
//        if (lowerMessage.contains("exercise") || lowerMessage.contains("workout") || lowerMessage.contains("training")) {
//            if (lowerMessage.contains("arm") || lowerMessage.contains("bicep") || lowerMessage.contains("tricep")) {
//                return getExerciseRecommendation("arm");
//            } else if (lowerMessage.contains("leg") || lowerMessage.contains("quad") || lowerMessage.contains("hamstring")) {
//                return getExerciseRecommendation("leg");
//            } else if (lowerMessage.contains("chest") || lowerMessage.contains("pec")) {
//                return getExerciseRecommendation("chest");
//            } else if (lowerMessage.contains("back") || lowerMessage.contains("lat")) {
//                return getExerciseRecommendation("back");
//            } else if (lowerMessage.contains("core") || lowerMessage.contains("abs")) {
//                return getExerciseRecommendation("core");
//            } else {
//                return "I can suggest exercises for different muscle groups like arms, legs, chest, back, or core. Which are you interested in?";
//            }
//        }
//
//        // Equipment-specific exercise queries
//        if (lowerMessage.contains("dumbbell") || lowerMessage.contains("barbell") || lowerMessage.contains("kettlebell") ||
//                lowerMessage.contains("equipment") || lowerMessage.contains("machine")) {
//            if (lowerMessage.contains("dumbbell")) {
//                return getExerciseByEquipment("dumbbell");
//            } else if (lowerMessage.contains("barbell")) {
//                return getExerciseByEquipment("barbell");
//            } else if (lowerMessage.contains("kettlebell")) {
//                return getExerciseByEquipment("kettlebell");
//            } else if (lowerMessage.contains("bodyweight") || lowerMessage.contains("no equipment")) {
//                return getExerciseByEquipment("bodyweight");
//            } else {
//                return "I can suggest exercises with different equipment like dumbbells, barbells, kettlebells, or bodyweight. What equipment do you have access to?";
//            }
//        }

        // Health metrics queries
        if (lowerMessage.contains("bmi") || lowerMessage.contains("weight") || lowerMessage.contains("health") ||
                lowerMessage.contains("metrics") || lowerMessage.contains("statistics")) {
            return "Based on our health data, the average BMI is around 24.5. Maintaining a BMI between 18.5 and 24.9 is generally considered healthy. Would you like specific advice on weight management?";
        }

        // Default response
        return "Hello! I'm your wellness assistant. I can help with diet recipes, exercise recommendations, or general health advice. What would you like to know about?";
    }


    private String getFilteredDietRecommendation(String message) {
        String dietType = null;
        if (message.contains("vegan") || message.contains("vegetarian")) dietType = "Vegetarian";
        else if (message.contains("keto") || message.contains("low carb")) dietType = "Keto";
        else if (message.contains("high protein") || message.contains("protein")) dietType = "High Protein";

        String cuisineType = null;
        if (message.contains("american")) cuisineType = "American";
        else if (message.contains("south east asian")) cuisineType = "South East Asian";

        Double maxFat = null;
        if (message.contains("low fat")) maxFat = 10.0;

        Double maxCarbs = null;
        if (message.contains("low carb")) maxCarbs = 20.0;

        Double minProtein = null;
        if (message.contains("high protein")) minProtein = 20.0;

        List<Map<String, String>> recipes = dataService.filterDietData(dietType, cuisineType, maxFat, maxCarbs, minProtein);
        if (recipes.isEmpty()) {
            return "Sorry, I couldn't find any recipes matching your preferences.";
        }

        Collections.shuffle(recipes);
        int numRecipes = Math.min(recipes.size(), 3);
        StringBuilder response = new StringBuilder("Here are some recipes you might like:\n");

        for (int i = 0; i < numRecipes; i++) {
            Map<String, String> recipe = recipes.get(i);
            response.append(i + 1).append(". ").append(recipe.get("Recipe_name"))
                    .append(" (").append(recipe.get("Cuisine_type")).append(" cuisine, ")
                    .append(recipe.get("Diet_type")).append(").\n")
                    .append("   Nutrition - Protein: ").append(recipe.get("Protein(g)")).append("g, ")
                    .append("Carbs: ").append(recipe.get("Carbs(g)")).append("g, ")
                    .append("Fat: ").append(recipe.get("Fat(g)")).append("g.\n\n");
        }

        return response.toString().trim();
    }

    private String getHealthMetricsByAgeAndGender(String message) {
        try {
            // Extract age using regex (get the first number found)
            int age = Integer.parseInt(message.replaceAll("[^0-9]", ""));

            // Determine gender from message
            String gender;
            if (message.contains("female")) {
                gender = "Female";
            } else if (message.contains("male")) {
                gender = "Male";
            } else {
                return "Please specify your gender (male or female) so I can provide accurate health metrics.";
            }

            // Fetch matching health metrics
            List<Map<String, String>> metrics = dataService.findHealthMetricsByAgeAndGender(age, gender);
            if (metrics.isEmpty()) {
                return "I couldn't find specific health metrics for age " + age + " and gender " + gender + ". Please try a different age.";
            }

            Map<String, String> metric = metrics.get(0); // Get the first match
            return String.format("Here are some health metrics for a %d-year-old %s:\n- BMI: %s\n- Average Heart Rate: %s bpm\n- Blood Pressure: %s mmHg",
                    age, gender,
                    metric.getOrDefault("BMI", "N/A"),
                    metric.getOrDefault("HeartRate", "N/A"),
                    metric.getOrDefault("BloodPressure", "N/A"));
        } catch (NumberFormatException e) {
            return "I couldn't identify your age. Please mention your age in digits.";
        } catch (Exception e) {
            return "Something went wrong while fetching your health metrics. Please try again.";
        }
    }

    private double averageOf(List<Map<String, String>> data, String key) {
        return data.stream()
                .mapToDouble(row -> {
                    try {
                        return Double.parseDouble(row.get(key));
                    } catch (Exception e) {
                        return 0.0;
                    }
                })
                .average().orElse(0.0);
    }

    private String getExerciseRecommendation(String muscleGroup) {
        List<Map<String, String>> exercises = dataService.findExercisesByMuscleGroup(muscleGroup);

        if (exercises.isEmpty()) {
            return "I couldn't find any exercises for that muscle group. Try another one?";
        }

        StringBuilder response = new StringBuilder("Here are some exercises for your " + muscleGroup + ":\n");

        int limit = Math.min(exercises.size(), 5);
        for (int i = 0; i < limit; i++) {
            String name = exercises.get(i).get("Exercise_Name");
            response.append(i + 1).append(". ").append(name).append("\n");
        }

        response.append("\nReply with the name of the exercise to get full details.");
        return response.toString();
    }

    private String getExerciseDetailsByName(String message) {
        Map<String, String> exercise = dataService.getExerciseDetailsByName(message.trim());

        if (exercise == null) {
            return "Sorry, I couldn't find detailed information on that exercise.";
        }

        return "**" + exercise.get("Exercise_Name") + "**\n"
                + "⭐ Rating: " + exercise.get("Rating") + "\n"
                + "💪 Muscle Group: " + exercise.get("muscle_gp") + " - " + exercise.get("muscle_gp_details") + "\n"
                + "🏋️ Equipment: " + exercise.get("Equipment") + " - " + exercise.get("equipment_details") + "\n"
                + "🔗 Description URL: " + exercise.get("Description_URL") + "\n"
                + "🖼️ Images: \n"
                + "   • " + exercise.get("Exercise_Image") + "\n"
                + "   • " + exercise.get("Exercise_Image1") + "\n"
                + "📝 Description: " + exercise.get("Description");
    }



//    private String getExerciseRecommendation(String muscleGroup) {
//        List<Map<String, String>> exercises = dataService.findExercisesByMuscleGroup(muscleGroup);
//        if (exercises.isEmpty()) {
//            return "Sorry, I couldn't find any exercises for " + muscleGroup + " in our database.";
//        }
//
//        Map<String, String> exercise = exercises.get(random.nextInt(exercises.size()));
//        return "Try this exercise for your " + muscleGroup + ": " + exercise.get("Exercise_Name") +
//                ". Equipment needed: " + exercise.get("Equipment") + ". " +
//                "Rating: " + exercise.get("Rating") + "/10. " +
//                exercise.get("Description");
//    }
//
//    private String getExerciseByEquipment(String equipment) {
//        List<Map<String, String>> exercises = dataService.findExercisesByEquipment(equipment);
//        if (exercises.isEmpty()) {
//            return "Sorry, I couldn't find any exercises using " + equipment + " in our database.";
//        }
//
//        Map<String, String> exercise = exercises.get(random.nextInt(exercises.size()));
//        return "Here's an exercise using " + equipment + ": " + exercise.get("Exercise_Name") +
//                ". Targets: " + exercise.get("muscle_gp") + ". " +
//                "Rating: " + exercise.get("Rating") + "/10. " +
//                exercise.get("Description");
//    }
}