package com.helmy.ecommerce.Repository;

import com.helmy.ecommerce.Model.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
    Optional<EmailVerificationToken> findByToken(String token);
    void deleteByExpiryDateBefore(LocalDateTime now);
}
