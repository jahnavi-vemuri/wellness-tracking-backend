package edu.indiana.se2.Wellness.Tracker.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class MealEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private LocalDate date;

    private String mealType; // e.g., "BREAKFAST", "LUNCH", etc.

    @Column(length = 1000)
    private String description;

    private Double calories;

    // Constructors, getters, and setters

    public MealEntry() {
    }

    public MealEntry(Long userId, LocalDate date, String mealType, String description, Double calories) {
        this.userId = userId;
        this.date = date;
        this.mealType = mealType;
        this.description = description;
        this.calories = calories;
    }

    // Getters and setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }
}
