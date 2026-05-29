package com.cloudpos.notification.email;

import com.cloudpos.notification.dto.EmailResponseDTO;
import com.cloudpos.notification.dto.SendEmailRequestDTO;
import com.cloudpos.notification.entity.EmailLog;
import com.cloudpos.notification.exception.EmailDeliveryException;
import com.cloudpos.notification.mapper.NotificationMapper;
import com.cloudpos.notification.notification.NotificationStatus;
import com.cloudpos.notification.repository.EmailLogRepository;
import com.cloudpos.notification.template.NotificationTemplateService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final String fromEmail;

    private final JavaMailSender mailSender;
    private final EmailLogRepository emailLogRepository;
    private final NotificationMapper notificationMapper;
    private final NotificationTemplateService templateService;

    public EmailServiceImpl(JavaMailSender mailSender, EmailLogRepository emailLogRepository,
            NotificationMapper notificationMapper, NotificationTemplateService templateService,
            @org.springframework.beans.factory.annotation.Value("${app.mail.from}") String fromEmail) {
        this.mailSender = mailSender;
        this.emailLogRepository = emailLogRepository;
        this.notificationMapper = notificationMapper;
        this.templateService = templateService;
        this.fromEmail = fromEmail;
    }

    @Override
    public EmailResponseDTO sendPlainEmail(SendEmailRequestDTO request) {
        EmailLog logEntry = createLog(request);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(request.getRecipientEmail());
            message.setSubject(request.getSubject());
            message.setText(request.getBody());
            mailSender.send(message);
            markSent(logEntry, "SMTP plain email accepted");
        } catch (RuntimeException ex) {
            markFailed(logEntry, ex);
            throw new EmailDeliveryException("Email delivery failed", ex);
        }
        return notificationMapper.toEmailResponse(emailLogRepository.save(logEntry));
    }

    @Override
    public EmailResponseDTO sendHtmlEmail(SendEmailRequestDTO request) {
        EmailLog logEntry = createLog(request);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(request.getRecipientEmail());
            helper.setSubject(request.getSubject());
            helper.setText(request.getBody(), true);
            mailSender.send(message);
            markSent(logEntry, "SMTP HTML email accepted");
        } catch (MessagingException | RuntimeException ex) {
            markFailed(logEntry, ex);
            throw new EmailDeliveryException("HTML email delivery failed", ex);
        }
        return notificationMapper.toEmailResponse(emailLogRepository.save(logEntry));
    }

    @Override
    public EmailResponseDTO sendTemplateEmail(SendEmailRequestDTO request) {
        String renderedBody = templateService.render(request.getTemplateName(), request.getTemplateVariables());
        request.setBody(renderedBody);
        request.setHtml(true);
        return sendHtmlEmail(request);
    }

    private EmailLog createLog(SendEmailRequestDTO request) {
        return emailLogRepository.save(EmailLog.builder()
                .notificationId(request.getNotificationId())
                .recipientEmail(request.getRecipientEmail())
                .subject(request.getSubject())
                .body(request.getBody())
                .status(NotificationStatus.PENDING)
                .build());
    }

    private void markSent(EmailLog logEntry, String providerResponse) {
        logEntry.setStatus(NotificationStatus.SENT);
        logEntry.setSentAt(LocalDateTime.now());
        logEntry.setProviderResponse(providerResponse);
        log.info("Email sent to {}", logEntry.getRecipientEmail());
    }

    private void markFailed(EmailLog logEntry, Exception ex) {
        logEntry.setStatus(NotificationStatus.FAILED);
        logEntry.setErrorMessage(ex.getMessage());
        logEntry.setRetryCount(logEntry.getRetryCount() + 1);
        emailLogRepository.save(logEntry);
        log.warn("Email delivery failed for {}", logEntry.getRecipientEmail(), ex);
    }
}
