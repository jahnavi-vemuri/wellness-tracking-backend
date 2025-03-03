package edu.indiana.se2.Wellness.Tracker.controller;

import edu.indiana.se2.Wellness.Tracker.entity.MentalWellbeingEntry;
import edu.indiana.se2.Wellness.Tracker.services.mentalWellbeing.MentalWellbeingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mental-wellbeing")
public class MentalWellbeingController {

    private final MentalWellbeingService service;

    @Autowired
    public MentalWellbeingController(MentalWellbeingService service) {
        this.service = service;
    }

    // Endpoint to log a new mental well-being entry
    @PostMapping("/log")
    public ResponseEntity<MentalWellbeingEntry> logEntry(@RequestBody MentalWellbeingEntry entry) {
        try {
            MentalWellbeingEntry savedEntry = service.logEntry(entry);
            return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to retrieve entries for a given user
    @GetMapping("/logs")
    public ResponseEntity<List<MentalWellbeingEntry>> getUserEntries(@PathVariable Long userId) {
        List<MentalWellbeingEntry> entries = service.getEntriesForUser(userId);
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }
}