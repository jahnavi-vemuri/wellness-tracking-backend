package edu.indiana.se2.Wellness.Tracker.services;

import edu.indiana.se2.Wellness.Tracker.model.Customer;
import edu.indiana.se2.Wellness.Tracker.repository.IAuthenticationRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public abstract class AuthenticationService implements IAuthenticationService {

    private final IAuthenticationRepository authenticationRepository;

    public AuthenticationService(IAuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public boolean register(Customer customer) throws IOException {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String passwordEncoded = bc.encode(customer.getPassword());
        customer.setPassword(passwordEncoded);
        return authenticationRepository.save(customer);
    }

//    @Override
//    public boolean login(String username, String password) throws IOException {
//        // Implement login logic if needed
//        return false;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Customer customer = authenticationRepository.findByUsername(username);
//        if (customer == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        return User.withUsername(username)
//        .password(customer.getPassword())
//        .authorities(new String[] {}) // Empty authorities
//        .build();
//
//    }
}
