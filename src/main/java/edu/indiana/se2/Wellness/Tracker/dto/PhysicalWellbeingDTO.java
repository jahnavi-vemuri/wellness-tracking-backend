package edu.indiana.se2.Wellness.Tracker.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class PhysicalWellbeingDTO {
    private String username;
    private LocalDate date;

    private int steps;

    private double distance;

    private int caloriesBurned;

    private int weight;

    private String workoutType;
}
