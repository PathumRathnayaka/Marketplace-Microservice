package com.cloudpos.notification.service;

import com.cloudpos.notification.dto.NotificationResponseDTO;
import com.cloudpos.notification.dto.SendEmailRequestDTO;
import com.cloudpos.notification.dto.SendNotificationRequestDTO;
import com.cloudpos.notification.dto.SendSmsRequestDTO;
import com.cloudpos.notification.email.EmailService;
import com.cloudpos.notification.entity.Notification;
import com.cloudpos.notification.exception.NotificationException;
import com.cloudpos.notification.exception.ResourceNotFoundException;
import com.cloudpos.notification.mapper.NotificationMapper;
import com.cloudpos.notification.notification.NotificationChannel;
import com.cloudpos.notification.notification.NotificationStatus;
import com.cloudpos.notification.push.PushNotificationService;
import com.cloudpos.notification.repository.NotificationRepository;
import com.cloudpos.notification.sms.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final EmailService emailService;
    private final SmsService smsService;
    private final PushNotificationService pushNotificationService;

    @Override
    @Transactional
    public NotificationResponseDTO sendNotification(SendNotificationRequestDTO request) {
        Notification notification = notificationRepository.save(notificationMapper.toEntity(request));
        try {
            dispatch(notification);
            if (notification.getChannel() == NotificationChannel.EMAIL) {
                notification.setStatus(NotificationStatus.SENT);
                notification.setSentAt(LocalDateTime.now());
            }
        } catch (RuntimeException ex) {
            notification.setStatus(NotificationStatus.FAILED);
            notificationRepository.save(notification);
            throw new NotificationException("Notification dispatch failed", ex);
        }
        return notificationMapper.toResponse(notificationRepository.save(notification));
    }

    @Override
    public List<NotificationResponseDTO> getNotifications() {
        return notificationRepository.findAll().stream()
                .filter(notification -> !notification.isDeleted())
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Override
    public NotificationResponseDTO getNotificationById(String id) {
        return notificationMapper.toResponse(findActiveById(id));
    }

    @Override
    @Transactional
    public NotificationResponseDTO markAsRead(String id) {
        Notification notification = findActiveById(id);
        notification.setStatus(NotificationStatus.READ);
        notification.setReadAt(LocalDateTime.now());
        return notificationMapper.toResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional
    public void softDelete(String id) {
        Notification notification = findActiveById(id);
        notification.setDeleted(true);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void updateStatus(String id, NotificationStatus status) {
        Notification notification = findActiveById(id);
        notification.setStatus(status);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void retryFailedNotifications() {
        List<Notification> failedNotifications = notificationRepository.findByStatusAndDeletedFalse(NotificationStatus.FAILED);
        failedNotifications.forEach(notification -> {
            try {
                notification.setStatus(NotificationStatus.RETRYING);
                notificationRepository.save(notification);
                dispatch(notification);
                notification.setStatus(NotificationStatus.SENT);
                notification.setSentAt(LocalDateTime.now());
            } catch (RuntimeException ex) {
                notification.setStatus(NotificationStatus.FAILED);
                log.warn("Retry failed for notification {}", notification.getId(), ex);
            }
            notificationRepository.save(notification);
        });
    }

    private void dispatch(Notification notification) {
        if (notification.getChannel() == NotificationChannel.EMAIL) {
            emailService.sendPlainEmail(SendEmailRequestDTO.builder()
                    .notificationId(notification.getId())
                    .recipientEmail(notification.getRecipient())
                    .subject(notification.getTitle())
                    .body(notification.getMessage())
                    .build());
            return;
        }
        if (notification.getChannel() == NotificationChannel.SMS) {
            smsService.sendSms(SendSmsRequestDTO.builder()
                    .notificationId(notification.getId())
                    .phoneNumber(notification.getRecipient())
                    .message(notification.getMessage())
                    .build());
            return;
        }
        if (notification.getChannel() == NotificationChannel.PUSH) {
            pushNotificationService.preparePushNotification(notification);
        }
    }

    private Notification findActiveById(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found: " + id));
        if (notification.isDeleted()) {
            throw new ResourceNotFoundException("Notification not found: " + id);
        }
        return notification;
    }
}
