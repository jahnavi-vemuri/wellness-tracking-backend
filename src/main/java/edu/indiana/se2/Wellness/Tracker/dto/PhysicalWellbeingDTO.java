package edu.indiana.se2.Wellness.Tracker.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PhysicalWellbeingDTO {
    private Long id;
    private Date date;

    private int steps;

    private double distance;

    private int caloriesBurned;

    private int weight;

    private String workoutType;
}
