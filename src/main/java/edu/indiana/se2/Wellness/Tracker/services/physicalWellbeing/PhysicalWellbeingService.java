package edu.indiana.se2.Wellness.Tracker.services.physicalWellbeing;

import edu.indiana.se2.Wellness.Tracker.entity.PhysicalWellbeingEntry;
import edu.indiana.se2.Wellness.Tracker.repository.PhysicalWellbeingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhysicalWellbeingService {

    private final PhysicalWellbeingRepository repository;

    @Autowired
    public PhysicalWellbeingService(PhysicalWellbeingRepository repository) {
        this.repository = repository;
    }

    public PhysicalWellbeingEntry logEntry(PhysicalWellbeingEntry entry) {
        Optional<PhysicalWellbeingEntry> existing = repository.findByUsernameAndDate(entry.getUsername(), entry.getDate());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Entry for this day already exists.");
        }
        return repository.save(entry);
    }

    public List<PhysicalWellbeingEntry> getEntriesForUser(String username) {
        return repository.findByUsername(username);
    }
}
