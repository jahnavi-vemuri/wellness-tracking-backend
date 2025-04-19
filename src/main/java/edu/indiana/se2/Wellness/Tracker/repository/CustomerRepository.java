package edu.indiana.se2.Wellness.Tracker.repository;

import edu.indiana.se2.Wellness.Tracker.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByEmailId(String emailId);
}
