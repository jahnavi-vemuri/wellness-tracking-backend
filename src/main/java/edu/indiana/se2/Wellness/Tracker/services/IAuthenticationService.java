package edu.indiana.se2.Wellness.Tracker.services;

import edu.indiana.se2.Wellness.Tracker.model.Customer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface IAuthenticationService {
    boolean register(Customer customer) throws IOException;
    boolean login(String username, String password) throws IOException;
    public Customer getCustomerByUsername(String username);
    }
