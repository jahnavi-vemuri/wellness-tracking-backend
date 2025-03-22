package edu.indiana.se2.Wellness.Tracker.repository;

import edu.indiana.se2.Wellness.Tracker.entity.MentalWellbeingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MentalWellbeingRepository extends JpaRepository<MentalWellbeingEntry, Long> {
    List<MentalWellbeingEntry> findByUsername(String username);
    Optional<MentalWellbeingEntry> findByUsernameAndDate(String username, LocalDate date);
}
