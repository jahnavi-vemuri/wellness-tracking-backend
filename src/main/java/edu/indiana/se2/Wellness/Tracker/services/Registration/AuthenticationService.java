package edu.indiana.se2.Wellness.Tracker.services.Registration;

import edu.indiana.se2.Wellness.Tracker.model.Customer;
import edu.indiana.se2.Wellness.Tracker.repository.AuthenticationDBRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
public class AuthenticationService implements IAuthenticationService, org.springframework.security.core.userdetails.UserDetailsService {

    private final AuthenticationDBRepository authenticationRepository;

    public AuthenticationService(AuthenticationDBRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public Customer register(Customer customer) throws IOException {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String passwordEncoded = bc.encode(customer.getPassword());
        customer.setPassword(passwordEncoded);
        // Optionally set a default role if not provided (e.g., "USER")
        if (customer.getRole() == null || customer.getRole().isEmpty()) {
            customer.setRole("USER");
        }
        return authenticationRepository.save(customer);
    }

    @Override
    public boolean login(String username, String password) throws IOException {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Customer customer = authenticationRepository.findByUsername(username);
            if (customer == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            // Map the customer's role to a GrantedAuthority
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + customer.getRole().toUpperCase());
            return User.withUsername(username)
                    .password(customer.getPassword())
                    .authorities(Collections.singletonList(authority))
                    .build();
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Error loading user", e);
        }
    }
}
