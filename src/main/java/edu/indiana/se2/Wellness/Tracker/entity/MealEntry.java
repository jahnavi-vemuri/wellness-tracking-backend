package edu.indiana.se2.Wellness.Tracker.entity;

import edu.indiana.se2.Wellness.Tracker.dto.MealEntryDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "meal_entry")
@Getter
@Setter
public class MealEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String mealType; // Breakfast, Lunch, Dinner, Snack

    @Column(columnDefinition = "TEXT")
    private String description; // Description of the meal

    @Column(nullable = false)
    private Double calories; // Total calories consumed

    @Column(nullable = false)
    private Double water; // Water intake in liters

    public MealEntryDTO getMealEntryDTO() {
        MealEntryDTO dto = new MealEntryDTO();
        dto.setUserId(userId);
        dto.setDate(date);
        dto.setMealType(mealType);
        dto.setDescription(description);
        dto.setCalories(calories);
        dto.setWater(water);
        return dto;
    }
}
