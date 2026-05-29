package com.cloudpos.auth.repository;

import com.cloudpos.auth.entity.EmailVerificationToken;
import com.cloudpos.common.enums.VerificationPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, String> {
    Optional<EmailVerificationToken> findByEmailAndOtpCodeAndPurposeAndVerifiedFalse(String email, String otpCode,
            VerificationPurpose purpose);

    Optional<EmailVerificationToken> findTopByEmailAndPurposeAndVerifiedTrueOrderByCreatedAtDesc(String email,
            VerificationPurpose purpose);
}
