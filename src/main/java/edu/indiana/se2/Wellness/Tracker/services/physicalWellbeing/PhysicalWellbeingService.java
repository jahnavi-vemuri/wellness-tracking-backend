package edu.indiana.se2.Wellness.Tracker.services.physicalWellbeing;

import edu.indiana.se2.Wellness.Tracker.dto.PhysicalWellbeingDTO;
import edu.indiana.se2.Wellness.Tracker.entity.PhysicalWellbeingEntry;
import edu.indiana.se2.Wellness.Tracker.repository.PhysicalWellbeingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhysicalWellbeingService {

    private final PhysicalWellbeingRepository repository;

    @Autowired
    public PhysicalWellbeingService(PhysicalWellbeingRepository repository) {
        this.repository = repository;
    }

    public PhysicalWellbeingDTO postActivity(PhysicalWellbeingDTO dto) {
        PhysicalWellbeingEntry entry = new PhysicalWellbeingEntry();
        entry.setDate(dto.getDate());
        entry.setSteps(dto.getSteps());
        entry.setDistance(dto.getDistance());
        entry.setCaloriesBurned(dto.getCaloriesBurned());

        PhysicalWellbeingEntry savedEntry = repository.save(entry);
        return savedEntry.getActivityDTO();
    }

    public List<PhysicalWellbeingDTO> getActivities() {
        return repository.findAll()
                .stream()
                .map(PhysicalWellbeingEntry::getActivityDTO)
                .collect(Collectors.toList());
    }
}
