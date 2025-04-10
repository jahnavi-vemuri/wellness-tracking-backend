package edu.indiana.se2.Wellness.Tracker.services.chatbot;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataService {
    private List<Map<String, String>> dietData = new ArrayList<>();
    private List<Map<String, String>> healthData = new ArrayList<>();
    private List<Map<String, String>> exerciseData = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            dietData = loadCsvData("src/main/resources/data/diet_data.csv");
            healthData = loadCsvData("src/main/resources/data/health_data.csv");
            exerciseData = loadCsvData("src/main/resources/data/exercise_data.csv");

            // Debugging: Print the loaded diet data
            System.out.println("Loaded diet data: " + dietData);
            System.out.println("Loaded health data: " + healthData);
            System.out.println("Loaded exercise data: " + exerciseData);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private List<Map<String, String>> loadCsvData(String filePath) throws IOException, CsvException {
        Path path = Paths.get(filePath);
        try (Reader reader = Files.newBufferedReader(path);
             CSVReader csvReader = new CSVReader(reader)) {

            String[] header = csvReader.readNext();
            List<String[]> rows = csvReader.readAll();

            // Debugging: Print header and rows
            System.out.println("CSV Header: " + Arrays.toString(header));
            System.out.println("CSV Rows: " + rows);

            return rows.stream().map(row -> {
                Map<String, String> rowMap = new HashMap<>();
                for (int i = 0; i < header.length; i++) {
                    if (i < row.length) {
                        rowMap.put(header[i], row[i]);
                    }
                }
                return rowMap;
            }).collect(Collectors.toList());
        }
    }

    public List<Map<String, String>> getDietData() {
        return dietData;
    }

    public List<Map<String, String>> getHealthData() {
        return healthData;
    }

    public List<Map<String, String>> getExerciseData() {
        return exerciseData;
    }

    public List<Map<String, String>> filterDietData(String dietType, String cuisineType, Double maxFat, Double maxCarbs, Double minProtein) {
        return dietData.stream()
                .filter(row -> dietType == null || row.get("Diet_type").equalsIgnoreCase(dietType))
                .filter(row -> cuisineType == null || row.get("Cuisine_type").equalsIgnoreCase(cuisineType))
                .filter(row -> {
                    try {
                        return maxFat == null || Double.parseDouble(row.get("Fat(g)")) <= maxFat;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .filter(row -> {
                    try {
                        return maxCarbs == null || Double.parseDouble(row.get("Carbs(g)")) <= maxCarbs;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .filter(row -> {
                    try {
                        return minProtein == null || Double.parseDouble(row.get("Protein(g)")) >= minProtein;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> findHealthMetricsByAgeAndGender(int age, String gender) {
        return healthData.stream()
                .filter(row -> {
                    try {
                        return Integer.parseInt(row.get("Age")) == age &&
                                row.get("Gender").equalsIgnoreCase(gender);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> findExercisesByMuscleGroup(String muscleGroup) {
        // Filter by muscle group
        return exerciseData.stream()
                .filter(data -> data.get("muscle_gp").toLowerCase().contains(muscleGroup.toLowerCase()))
                .collect(Collectors.toList());
    }

//    public List<Map<String, String>> findExercisesByEquipment(String equipment) {
//        // Filter by equipment
//        return exerciseData.stream()
//                .filter(data -> data.get("Equipment").toLowerCase().contains(equipment.toLowerCase()))
//                .collect(Collectors.toList());
//    }
public Map<String, String> getExerciseDetailsByName(String exerciseName) {
    return exerciseData.stream()
            .filter(ex -> ex.get("Exercise_Name").equalsIgnoreCase(exerciseName))
            .findFirst()
            .orElse(null);
}

}
