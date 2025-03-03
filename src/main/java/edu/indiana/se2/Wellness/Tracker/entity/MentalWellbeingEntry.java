package edu.indiana.se2.Wellness.Tracker.entity;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class MentalWellbeingEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDate date;

    // For example, a rating between 1 to 5
    private int moodRating;

    @Column(length = 1000)
    private String notes;

    public MentalWellbeingEntry() {}

    public MentalWellbeingEntry(Long userId, LocalDate date, int moodRating, String notes) {
        this.userId = userId;
        this.date = date;
        this.moodRating = moodRating;
        this.notes = notes;
    }

    // Getters and setters

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

    public int getMoodRating() {
        return moodRating;
    }

    public void setMoodRating(int moodRating) {
        this.moodRating = moodRating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
