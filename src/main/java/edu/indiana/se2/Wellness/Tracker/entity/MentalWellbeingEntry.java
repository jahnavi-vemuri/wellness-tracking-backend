package edu.indiana.se2.Wellness.Tracker.entity;

import edu.indiana.se2.Wellness.Tracker.dto.MentalWellbeingDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "mental_wellbeing_entry")
@Getter
@Setter
public class MentalWellbeingEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int moodRating;

    @Column(nullable = false)
    private int stressLevel;

    @Column(nullable = false)
    private int bedTime; // Store bedtime as HHMM (e.g., 2230 for 10:30 PM)

    @Column(nullable = false)
    private int wakeupTime; // Store wake-up time as HHMM (e.g., 0630 for 6:30 AM)

    @Column(nullable = false)
    private double screenTime; // Hours of screen usage

    @Column(columnDefinition = "TEXT")
    private String notes; // Optional journal entry

    public MentalWellbeingDTO getMentalWellbeingDTO() {
        MentalWellbeingDTO dto = new MentalWellbeingDTO();
        dto.setId(id);
        dto.setUserId(userId);
        dto.setDate(date);
        dto.setMoodRating(moodRating);
        dto.setStressLevel(stressLevel);
        dto.setBedTime(bedTime);
        dto.setWakeupTime(wakeupTime);
        dto.setScreenTime(screenTime);
        dto.setNotes(notes);
        return dto;
    }
}
