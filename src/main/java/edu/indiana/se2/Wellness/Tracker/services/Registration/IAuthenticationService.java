package edu.indiana.se2.Wellness.Tracker.services.Registration;

import edu.indiana.se2.Wellness.Tracker.model.Customer;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public interface IAuthenticationService {
    Customer register(Customer customer) throws IOException;
    boolean login(String username, String password) throws IOException;
}
