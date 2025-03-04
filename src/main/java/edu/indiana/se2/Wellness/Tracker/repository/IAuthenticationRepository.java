package edu.indiana.se2.Wellness.Tracker.repository;


import edu.indiana.se2.Wellness.Tracker.model.Customer;

import java.io.IOException;

public interface IAuthenticationRepository {
    boolean save(Customer customer) throws IOException;
    Customer findByUsername(String username) throws IOException;
}
