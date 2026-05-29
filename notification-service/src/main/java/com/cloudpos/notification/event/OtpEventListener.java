package com.cloudpos.notification.event;

import com.cloudpos.common.event.RegistrationOtpEventDTO;
import com.cloudpos.notification.dto.SendEmailRequestDTO;
import com.cloudpos.notification.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OtpEventListener {

    private static final Logger log = LoggerFactory.getLogger(OtpEventListener.class);
    private final EmailService emailService;

    public OtpEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "cloudpos.otp-requested.queue")
    public void handleOtpRequested(RegistrationOtpEventDTO event) {
        log.info("Received OTP request event for email: {}", event.getEmail());

        SendEmailRequestDTO emailRequest = new SendEmailRequestDTO();
        emailRequest.setRecipientEmail(event.getEmail());
        emailRequest.setSubject("Verification Code for " + event.getPurpose());
        emailRequest
                .setBody("Your verification code is: " + event.getOtpCode() + "\nThis code will expire in 5 minutes.");

        try {
            emailService.sendPlainEmail(emailRequest);
            log.info("OTP email sent successfully to {}", event.getEmail());
        } catch (Exception e) {
            log.error("Failed to send OTP email to {}: {}", event.getEmail(), e.getMessage());
        }
    }
}
