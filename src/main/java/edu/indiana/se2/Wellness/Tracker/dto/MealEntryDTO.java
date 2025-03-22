package edu.indiana.se2.Wellness.Tracker.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MealEntryDTO {
    private String username;
    private LocalDate date;
    private String mealType;
    private String description;
    private Double calories;
    private Double water;
}