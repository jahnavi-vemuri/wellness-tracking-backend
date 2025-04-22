package edu.indiana.se2.Wellness.Tracker.controller;

import edu.indiana.se2.Wellness.Tracker.entity.MentalWellbeingEntry;
import edu.indiana.se2.Wellness.Tracker.services.mentalWellbeing.MentalWellbeingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/mental-wellbeing")
@RequiredArgsConstructor
public class MentalWellbeingController {

    private final MentalWellbeingService service;

    @PostMapping("/log")
    public ResponseEntity<MentalWellbeingEntry> logEntry(@RequestBody MentalWellbeingEntry entry,
                                                         @AuthenticationPrincipal Jwt jwt) {
        try {
            String username = jwt.getSubject();
            entry.setUsername(username);
            MentalWellbeingEntry savedEntry = service.logEntry(entry);
            return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logs")
    public ResponseEntity<List<MentalWellbeingEntry>> getUserEntries(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<MentalWellbeingEntry> entries = service.getEntriesForUser(username);
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<Void> deleteMentalEntry(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        boolean deleted = service.deleteEntry(id, username);  // Service method to handle delete
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Successfully deleted
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Entry not found
        }
    }
}
