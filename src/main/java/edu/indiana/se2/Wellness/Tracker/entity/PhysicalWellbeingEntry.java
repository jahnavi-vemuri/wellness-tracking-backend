package edu.indiana.se2.Wellness.Tracker.entity;

import edu.indiana.se2.Wellness.Tracker.dto.PhysicalWellbeingDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "physical_wellbeing_entry")
@Getter
@Setter
public class PhysicalWellbeingEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

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
        PhysicalWellbeingDTO activityDTO = new PhysicalWellbeingDTO();
        activityDTO.setId(id);
        activityDTO.setDate(date);
        activityDTO.setSteps(steps);
        activityDTO.setDistance(distance);
        activityDTO.setCaloriesBurned(caloriesBurned);
        activityDTO.setWeight(weight);
        activityDTO.setWorkoutType(workoutType);
        return activityDTO;
    }
}
