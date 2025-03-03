package edu.indiana.se2.Wellness.Tracker.services;

import edu.indiana.se2.Wellness.Tracker.entity.MealEntry;
import edu.indiana.se2.Wellness.Tracker.repository.MealEntryRepository;
import edu.indiana.se2.Wellness.Tracker.services.mealEntry.MealEntryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MealEntryServiceTest {

    private MealEntryRepository mealEntryRepository;
    private MealEntryService mealEntryService;

    @BeforeEach
    public void setUp() {
        // Create a mock of MealEntryRepository using Mockito
        mealEntryRepository = Mockito.mock(MealEntryRepository.class);
        // Instantiate the service with the mocked repository
        mealEntryService = new MealEntryService(mealEntryRepository);
    }

    @Test
    public void testUploadMeal_Success() {
        // Arrange: Create a sample meal entry.
        MealEntry meal = new MealEntry(1L, LocalDate.now(), "LUNCH", "Grilled chicken salad", 450.0);
        // Stub the repository save method to return the provided meal entry.
        when(mealEntryRepository.save(any(MealEntry.class))).thenReturn(meal);

        // Act: Call the service method.
        MealEntry savedMeal = mealEntryService.uploadMeal(meal);

        // Assert: Verify the result is not null and that fields match.
        assertNotNull(savedMeal, "Saved meal should not be null");
        assertEquals(1L, savedMeal.getUserId(), "User ID should match");
        assertEquals("LUNCH", savedMeal.getMealType(), "Meal type should match");
        assertEquals("Grilled chicken salad", savedMeal.getDescription(), "Description should match");
        assertEquals(450.0, savedMeal.getCalories(), "Calories should match");

        // Verify that the repository's save method was called exactly once.
        verify(mealEntryRepository, times(1)).save(meal);
    }

    @Test
    public void testGetMealsForUser() {
        // Arrange: Set up a user ID and a list of meal entries for that user.
        Long userId = 1L;
        MealEntry meal1 = new MealEntry(userId, LocalDate.now(), "BREAKFAST", "Oatmeal with fruits", 300.0);
        MealEntry meal2 = new MealEntry(userId, LocalDate.now(), "DINNER", "Steak with vegetables", 700.0);
        List<MealEntry> mealList = Arrays.asList(meal1, meal2);
        // Stub the repository method findByUserId to return the prepared list.
        when(mealEntryRepository.findByUserId(userId)).thenReturn(mealList);

        // Act: Retrieve meals for the user.
        List<MealEntry> retrievedMeals = mealEntryService.getMealsForUser(userId);

        // Assert: Verify the retrieved list matches the expected size and contents.
        assertNotNull(retrievedMeals, "Retrieved meals list should not be null");
        assertEquals(2, retrievedMeals.size(), "Should retrieve two meal entries");
        assertEquals("BREAKFAST", retrievedMeals.get(0).getMealType(), "First meal type should be BREAKFAST");
        assertEquals("DINNER", retrievedMeals.get(1).getMealType(), "Second meal type should be DINNER");

        // Verify the repository method was called exactly once.
        verify(mealEntryRepository, times(1)).findByUserId(userId);
    }
}

