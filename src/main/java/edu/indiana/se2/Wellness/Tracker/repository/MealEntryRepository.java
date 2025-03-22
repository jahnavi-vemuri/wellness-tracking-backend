package edu.indiana.se2.Wellness.Tracker.repository;

import edu.indiana.se2.Wellness.Tracker.entity.MealEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MealEntryRepository extends JpaRepository<MealEntry, Long> {
    List<MealEntry> findByUsername(String username);
    Optional<MealEntry> findByUsernameAndDate(String username, LocalDate date);
}
