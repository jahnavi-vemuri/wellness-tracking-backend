package edu.indiana.se2.Wellness.Tracker.controller;

import edu.indiana.se2.Wellness.Tracker.entity.MealEntry;
import edu.indiana.se2.Wellness.Tracker.services.mealEntry.MealEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/meals")
@RequiredArgsConstructor
public class MealEntryController {

    private final MealEntryService mealEntryService;

    @PostMapping("/upload")
    public ResponseEntity<MealEntry> uploadMeal(@RequestBody MealEntry mealEntry,
                                                @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject(); // 🔐 Extract username from JWT
        mealEntry.setUsername(username);

        MealEntry savedMeal = mealEntryService.uploadMeal(mealEntry);
        return new ResponseEntity<>(savedMeal, HttpStatus.CREATED);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<MealEntry>> getMealsForUser(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<MealEntry> meals = mealEntryService.getMealsForUser(username);
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<Void> deleteMealEntry(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        boolean deleted = mealEntryService.deleteEntry(id, username);  // Service method to handle delete
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Successfully deleted
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Entry not found
        }
    }
}
