package edu.indiana.se2.Wellness.Tracker.controller;

import edu.indiana.se2.Wellness.Tracker.model.Customer;
import edu.indiana.se2.Wellness.Tracker.repository.CustomerRepository;
import edu.indiana.se2.Wellness.Tracker.services.TOTPService;
import edu.indiana.se2.Wellness.Tracker.services.TokenService;
import edu.indiana.se2.Wellness.Tracker.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/2fa")
@CrossOrigin("**")
public class TwoFactorController {

    @Autowired
    private TOTPService totpService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired AuthenticationService authenticationService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/generate")
    public Map<String, String> generateSecret(@RequestParam String email) {
        Map<String, String> response = new HashMap<>();
        try {
            String secret = totpService.generateSecret();
            System.out.println("XXX - secret generated");
            String qrCode = totpService.generateQrCode(secret, email);
            System.out.println("XXX - Qr Code generated");
            response.put("secret", secret);
            response.put("qrCodeUrl", qrCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("error", "Failed to generate QR code.");
        }
        return response;
    }

    @PostMapping("/verify")
    public Map<String, Boolean> verify(@RequestBody Map<String, String> payload) {
        String secret = payload.get("secret");
        String code = payload.get("code");

        boolean isValid = totpService.verifyCode(secret, code);

        Map<String, Boolean> result = new HashMap<>();
        result.put("valid", isValid);
        return result;
    }

    @PostMapping("/verify-login")
    public ResponseEntity<?> verifyLogin2FA(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String code = payload.get("code");

        Customer customer = customerRepository.findByUsername(username);
        if (customer == null){
            return ResponseEntity.status(404).body("2FA not enabled for user");
        }
        String secret = customer.getTotpSecret();
        if (secret == null || secret.isBlank()){
            return ResponseEntity.status(400).body("2FA not enabled for user");
        }

        boolean isValid = totpService.verifyCode(secret, code);
        if (!isValid){
            return ResponseEntity.status(403).body("Invalid 2FA Code");
        }

        //Reauthenticating user
        UserDetails userDetails = authenticationService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        String token = tokenService.generateToken(authentication);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", customer);
        return ResponseEntity.ok(response);
    }

}
