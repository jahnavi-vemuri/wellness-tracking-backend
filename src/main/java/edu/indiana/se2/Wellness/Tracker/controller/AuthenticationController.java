package edu.indiana.se2.Wellness.Tracker.controller;

import edu.indiana.se2.Wellness.Tracker.model.Customer;

import edu.indiana.se2.Wellness.Tracker.services.IAuthenticationService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping
@CrossOrigin("*")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;
//    private final AuthenticationManager authenticationManager;
//    private final TokenService tokenService;

    public AuthenticationController(
            IAuthenticationService authenticationService
//            AuthenticationManager authenticationManager,
    )
    {
        this.authenticationService = authenticationService;
//        this.authenticationManager = authenticationManager;
//        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public boolean register(@RequestBody Customer customer) {
        try {
            return authenticationService.register(customer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(
//                loginRequest.getUsername(),
//                loginRequest.getPassword()
//            )
//        );
//
//        String token = tokenService.generateToken(authentication);
//        Map<String, String> response = new HashMap<>();
//        response.put("token", token);
//        return ResponseEntity.ok(response);
//    }
}
