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
        // Prevent duplicate logs for the same user on the same day
        Optional<MentalWellbeingEntry> existingEntry = repository.findByUserIdAndDate(entry.getUserId(), entry.getDate());
        if (existingEntry.isPresent()) {
            throw new IllegalArgumentException("Entry for this day already exists.");
        }
        return repository.save(entry);
    }

    public List<MentalWellbeingEntry> getEntriesForUser(Long userId) {
        return repository.findByUserId(userId);
    }
}
