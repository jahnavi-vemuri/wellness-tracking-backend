package edu.indiana.se2.Wellness.Tracker.controller;

import edu.indiana.se2.Wellness.Tracker.dto.LoginRequest;
import edu.indiana.se2.Wellness.Tracker.model.Customer;
import edu.indiana.se2.Wellness.Tracker.repository.CustomerRepository;

import edu.indiana.se2.Wellness.Tracker.services.IAuthenticationService;
import edu.indiana.se2.Wellness.Tracker.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.beans.Customizer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin("**")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final CustomerRepository customerRepository;

    public AuthenticationController(
            IAuthenticationService authenticationService,
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            CustomerRepository customerRepository
    )
    {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/register")
    public boolean register(@RequestBody Customer customer) {
        try {
            return authenticationService.register(customer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );
        Customer customer = authenticationService.getCustomerByUsername(loginRequest.getUsername());

        if (customer != null && customer.getTotpSecret() != null && !customer.getTotpSecret().isBlank()){
            //This means that 2FA has been set up and required now
            Map<String, Object> response = new HashMap<>();
            response.put("requires2FA", true);
            return ResponseEntity.ok(response);
        }

        //If no 2FA, proceed as usual
        String token = tokenService.generateToken(authentication);
        Map<String, Object> response = new HashMap<>();
        response.put("requires2FA", false);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/users/set-totp-secret")
    public ResponseEntity<?> setTotpSecret(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String secret = payload.get("secret");

        if (email == null || secret == null) {
            return ResponseEntity.badRequest().body("Missing data");
        }

        Customer customer = customerRepository.findByEmailId(email);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        customer.setTotpSecret(secret);
        customerRepository.save(customer);
        return ResponseEntity.ok("TOTP secret saved successfully.");
    }

//    @PostMapping("/users/set-totp-secret")
//    public ResponseEntity<?> setTotpSecret(@RequestBody Map<String, String> payload) {
//        String email = payload.get("email");
//        String secret = payload.get("secret");
//
//        Customer customer = customerRepository.findByEmailId(email);
//        if (customer == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//
//        customer.setTotpSecret(secret);
//        customerRepository.save(customer);
//
//        return ResponseEntity.ok("TOTP secret saved successfully.");
//    }
}
