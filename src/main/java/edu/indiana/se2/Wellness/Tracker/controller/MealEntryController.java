package edu.indiana.se2.Wellness.Tracker.controller;

import edu.indiana.se2.Wellness.Tracker.entity.MealEntry;
import edu.indiana.se2.Wellness.Tracker.services.mealEntry.MealEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealEntryController {

    private final MealEntryService mealEntryService;

    @Autowired
    public MealEntryController(MealEntryService mealEntryService) {
        this.mealEntryService = mealEntryService;
    }

    // Endpoint to upload a new meal entry
    @PostMapping("/upload")
    public ResponseEntity<MealEntry> uploadMeal(@RequestBody MealEntry mealEntry) {
        MealEntry savedMeal = mealEntryService.uploadMeal(mealEntry);
        return new ResponseEntity<>(savedMeal, HttpStatus.CREATED);
    }

    // Endpoint to retrieve meal entries for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealEntry>> getMealsForUser(@PathVariable Long userId) {
        List<MealEntry> meals = mealEntryService.getMealsForUser(userId);
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }
}

