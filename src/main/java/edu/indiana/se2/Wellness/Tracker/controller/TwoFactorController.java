package edu.indiana.se2.Wellness.Tracker.controller;

import edu.indiana.se2.Wellness.Tracker.services.TOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/2fa")
@CrossOrigin("**")
public class TwoFactorController {

    @Autowired
    private TOTPService totpService;

    @GetMapping("/generate")
    public Map<String, String> generateSecret(@RequestParam String email) {
        Map<String, String> response = new HashMap<>();
        try {
            String secret = totpService.generateSecret();
            String qrCode = totpService.generateQrCode(secret, email);

            response.put("secret", secret);
            response.put("qrCodeUrl", qrCode);
        } catch (Exception e) {
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
}
