package edu.indiana.se2.Wellness.Tracker.services.mealEntry;

import edu.indiana.se2.Wellness.Tracker.entity.MealEntry;
import edu.indiana.se2.Wellness.Tracker.repository.MealEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealEntryService {

    private final MealEntryRepository mealEntryRepository;

    @Autowired
    public MealEntryService(MealEntryRepository mealEntryRepository) {
        this.mealEntryRepository = mealEntryRepository;
    }

    public MealEntry uploadMeal(MealEntry mealEntry) {
        // Optional: check for duplicates if needed
        return mealEntryRepository.save(mealEntry);
    }

    public List<MealEntry> getMealsForUser(String username) {
        return mealEntryRepository.findByUsername(username);
    }
}

