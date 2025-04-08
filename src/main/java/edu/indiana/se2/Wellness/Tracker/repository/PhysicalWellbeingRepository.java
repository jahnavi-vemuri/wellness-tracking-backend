package edu.indiana.se2.Wellness.Tracker.repository;

import edu.indiana.se2.Wellness.Tracker.entity.PhysicalWellbeingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PhysicalWellbeingRepository extends JpaRepository<PhysicalWellbeingEntry, Long> {
    List<PhysicalWellbeingEntry> findByUsername(String username);
    Optional<PhysicalWellbeingEntry> findByUsernameAndDate(String username, LocalDate date);
}
