package edu.indiana.se2.Wellness.Tracker.services.mentalWellbeing;

import edu.indiana.se2.Wellness.Tracker.entity.MentalWellbeingEntry;
import edu.indiana.se2.Wellness.Tracker.repository.MentalWellbeingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MentalWellbeingService {

    private final MentalWellbeingRepository repository;

    @Autowired
    public MentalWellbeingService(MentalWellbeingRepository repository) {
        this.repository = repository;
    }

    public MentalWellbeingEntry logEntry(MentalWellbeingEntry entry) {
        Optional<MentalWellbeingEntry> existing = repository.findByUsernameAndDate(entry.getUsername(), entry.getDate());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Entry for this day already exists.");
        }
        return repository.save(entry);
    }

    public List<MentalWellbeingEntry> getEntriesForUser(String username) {
        return repository.findByUsername(username);
    }

    public boolean deleteEntry(Long id, String username) {
        Optional<MentalWellbeingEntry> entry = repository.findById(id);
        if (entry.isPresent() && entry.get().getUsername().equals(username)) {
            repository.delete(entry.get());
            return true;
        }
        return false;  // Entry not found or user is not authorized to delete
    }
}
