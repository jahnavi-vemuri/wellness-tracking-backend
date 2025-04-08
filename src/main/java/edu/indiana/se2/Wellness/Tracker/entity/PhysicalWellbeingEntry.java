package edu.indiana.se2.Wellness.Tracker.entity;

import edu.indiana.se2.Wellness.Tracker.dto.PhysicalWellbeingDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "physical_wellbeing_entry")
@Getter
@Setter
public class PhysicalWellbeingEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ✅ Primary Key

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int steps;

    @Column(nullable = false)
    private double distance;

    @Column(nullable = false)
    private int caloriesBurned;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private String workoutType;

    public PhysicalWellbeingDTO getActivityDTO() {
        PhysicalWellbeingDTO dto = new PhysicalWellbeingDTO();
        dto.setUsername(username);
        dto.setDate(date);
        dto.setSteps(steps);
        dto.setDistance(distance);
        dto.setCaloriesBurned(caloriesBurned);
        dto.setWeight(weight);
        dto.setWorkoutType(workoutType);
        return dto;
    }
}
