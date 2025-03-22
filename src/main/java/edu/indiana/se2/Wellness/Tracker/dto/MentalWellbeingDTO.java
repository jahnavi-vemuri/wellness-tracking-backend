package edu.indiana.se2.Wellness.Tracker.dto;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MentalWellbeingDTO {
    private String username;
    private LocalDate date;
    private int moodRating;
    private int stressLevel;
    private int bedTime;
    private int wakeupTime;
    private double ScreenTime;
    private String notes;
}
