package edu.indiana.se2.Wellness.Tracker.controller;

import edu.indiana.se2.Wellness.Tracker.entity.PhysicalWellbeingEntry;
import edu.indiana.se2.Wellness.Tracker.services.physicalWellbeing.PhysicalWellbeingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/physical-wellbeing")
@RequiredArgsConstructor
public class PhysicalWellbeingController {

    private final PhysicalWellbeingService activityService;

    @PostMapping("/log")
    public ResponseEntity<PhysicalWellbeingEntry> logEntry(@RequestBody PhysicalWellbeingEntry entry,
                                                           @AuthenticationPrincipal Jwt jwt) {
        try {
            String username = jwt.getSubject();
            System.out.println("✅ Physical Entry requested by username: " + username);
            entry.setUsername(username);
            PhysicalWellbeingEntry savedEntry = activityService.logEntry(entry);
            return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            System.out.print("illegal argument when logging physical entry");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logs")
    public ResponseEntity<List<PhysicalWellbeingEntry>> getUserEntries(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        List<PhysicalWellbeingEntry> entries = activityService.getEntriesForUser(username);
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<Void> deletePhysicalEntry(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        boolean deleted = activityService.deleteEntry(id, username);  // Service method to handle delete
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Successfully deleted
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Entry not found
        }
    }
}
