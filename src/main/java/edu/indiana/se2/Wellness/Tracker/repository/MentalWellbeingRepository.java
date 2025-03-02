package edu.indiana.se2.Wellness.Tracker.repository;

import edu.indiana.se2.Wellness.Tracker.entity.MentalWellbeingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MentalWellbeingRepository extends JpaRepository<MentalWellbeingEntry, Long> {
    // Retrieve all logs for a given user
    List<MentalWellbeingEntry> findByUserId(Long userId);

    // To prevent duplicate logs per day by checking if an entry already exists
    Optional<MentalWellbeingEntry> findByUserIdAndDate(Long userId, LocalDate date);
}