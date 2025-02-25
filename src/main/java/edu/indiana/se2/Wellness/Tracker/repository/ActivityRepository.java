package edu.indiana.se2.Wellness.Tracker.repository;

import edu.indiana.se2.Wellness.Tracker.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}

