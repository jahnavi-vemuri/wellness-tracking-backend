package edu.indiana.se2.Wellness.Tracker.services;

import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrData.Builder;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import org.springframework.stereotype.Service;

import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class TOTPService {

    private final DefaultSecretGenerator secretGenerator = new DefaultSecretGenerator();
    private final DefaultCodeVerifier verifier =
            new DefaultCodeVerifier(new DefaultCodeGenerator(), new SystemTimeProvider());

    public String generateSecret() {
        return secretGenerator.generate();
    }

    public boolean verifyCode(String secret, String code) {
        return verifier.isValidCode(secret, code);
    }

    public String generateQrCode(String secret, String email) throws Exception {
        QrData qrData = new Builder()
                .label(email)
                .secret(secret)
                .issuer("WellnessTracker")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();

        ZxingPngQrGenerator qrGenerator = new ZxingPngQrGenerator();
        byte[] imageData = qrGenerator.generate(qrData);

        return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageData);
    }
}
