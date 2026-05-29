package com.cloudpos.auth.service;

import com.cloudpos.auth.config.RabbitMQConfig;
import com.cloudpos.auth.entity.EmailVerificationToken;
import com.cloudpos.auth.repository.EmailVerificationTokenRepository;
import com.cloudpos.common.enums.VerificationPurpose;
import com.cloudpos.common.event.RegistrationOtpEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpService {

    private final EmailVerificationTokenRepository tokenRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Random random = new Random();

    @Transactional
    public void requestOtp(String email, VerificationPurpose purpose) {
        String otp = String.format("%06d", random.nextInt(1000000));

        EmailVerificationToken token = EmailVerificationToken.builder()
                .email(email)
                .otpCode(otp)
                .purpose(purpose)
                .verified(false)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .attemptCount(0)
                .build();

        tokenRepository.save(token);

        RegistrationOtpEventDTO event = RegistrationOtpEventDTO.builder()
                .email(email)
                .otpCode(otp)
                .purpose(purpose)
                .createdAt(LocalDateTime.now())
                .build();

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_OTP_REQUESTED, event);
        log.info("OTP requested for email: {} with purpose: {}", email, purpose);
    }

    @Transactional
    public boolean verifyOtp(String email, String otp, VerificationPurpose purpose) {
        Optional<EmailVerificationToken> tokenOpt = tokenRepository
                .findByEmailAndOtpCodeAndPurposeAndVerifiedFalse(email, otp, purpose);

        if (tokenOpt.isEmpty()) {
            return false;
        }

        EmailVerificationToken token = tokenOpt.get();
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.warn("OTP expired for email: {}", email);
            return false;
        }

        if (token.getAttemptCount() >= 5) {
            log.warn("Max OTP attempts reached for email: {}", email);
            return false;
        }

        token.setVerified(true);
        tokenRepository.save(token);
        log.info("OTP verified for email: {} with purpose: {}", email, purpose);
        return true;
    }

    public boolean isEmailVerified(String email, VerificationPurpose purpose) {
        return tokenRepository.findTopByEmailAndPurposeAndVerifiedTrueOrderByCreatedAtDesc(email, purpose).isPresent();
    }
}
