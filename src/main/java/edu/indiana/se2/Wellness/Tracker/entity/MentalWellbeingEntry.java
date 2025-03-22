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
    private Long id; // ✅ Primary Key

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int moodRating;

    @Column(nullable = false)
    private int stressLevel;

    @Column(nullable = false)
    private int bedTime;

    @Column(nullable = false)
    private int wakeupTime;

    @Column(nullable = false)
    private double screenTime;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public MentalWellbeingDTO getMentalWellbeingDTO() {
        MentalWellbeingDTO dto = new MentalWellbeingDTO();
        dto.setUsername(username);
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
