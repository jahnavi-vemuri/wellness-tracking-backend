package edu.indiana.se2.Wellness.Tracker.services;

import edu.indiana.se2.Wellness.Tracker.model.Customer;
import edu.indiana.se2.Wellness.Tracker.repository.AuthenticationDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationService implements IAuthenticationService, UserDetailsService {

    @Autowired
    private final AuthenticationDBRepository authenticationRepository;

    public AuthenticationService(AuthenticationDBRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public boolean register(Customer customer) throws IOException {
        // Encrypt the password before saving it
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String passwordEncoded = bc.encode(customer.getPassword());
        customer.setPassword(passwordEncoded);

        // Check if the username already exists
        Customer existingCustomer = authenticationRepository.findByUsername(customer.getUsername());
        if (existingCustomer != null) {
            // Username already exists, cannot register
            return false;
        }

        // Save the customer to the database
        authenticationRepository.save(customer);
        return true;
    }

    @Override
    public boolean login(String username, String password) throws IOException {
        // Implement login logic if needed
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the customer by username from the database
        Customer customer = authenticationRepository.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // Return UserDetails object with username and password (authorities can be set as needed)
        return User.withUsername(username)
                .password(customer.getPassword())
                .authorities(new String[] {}) // Empty authorities
                .build();
    }
}
