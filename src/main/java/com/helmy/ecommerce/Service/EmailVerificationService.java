package com.helmy.ecommerce.Service;

import com.helmy.ecommerce.Model.EmailVerificationToken;
import com.helmy.ecommerce.Model.User;
import com.helmy.ecommerce.Repository.EmailVerificationTokenRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailVerificationService {

    private final EmailService emailService;
    private final EmailVerificationTokenRepository tokenRepository;

    @Autowired
    public EmailVerificationService(EmailService emailService, EmailVerificationTokenRepository tokenRepository) {
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

    public void createAndSendToken(User user) {
        int code = 10000 + new Random().nextInt(90000);

        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setToken(String.valueOf(code));
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        tokenRepository.save(verificationToken);

        System.out.println("Sending to email: " + user.getEmail() + " | code: " + code);

        String subject = "Ecommerce Egypt - Verify Your Account";

        // HTML message
        String htmlMessage = """
                <html>
                    <body>
                        <h2>Welcome to Ecommerce Egypt!</h2>
                        <p>Your verification code is: <b>%s</b></p>
                        <p>Or click the link to verify your email:</p>
                        <p><a href="http://localhost:8080/api/auth/verify?token=%s">Verify Account</a></p>
                        <br>
                        <p>This code will expire in 15 minutes.</p>
                        <p>Ecommerce Egypt - The best place to find everything you need online.</p>
                    </body>
                </html>
                """.formatted(code, code);

        emailService.sendEmail(user.getEmail(), subject, htmlMessage, true); // true = HTML
    }

    public boolean verifyToken(String token) {
        var optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isEmpty())
            return false;

        EmailVerificationToken verificationToken = optionalToken.get();

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(verificationToken);
            return false;
        }

        User user = verificationToken.getUser();
        user.setIsVerified(true);
        tokenRepository.delete(verificationToken);
        return true;
    }

    public void deleteExpiredTokens() {
        tokenRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }
}
