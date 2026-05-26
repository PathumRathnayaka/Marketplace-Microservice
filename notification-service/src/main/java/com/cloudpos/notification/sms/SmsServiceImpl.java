package com.cloudpos.notification.sms;

import com.cloudpos.notification.dto.SendSmsRequestDTO;
import com.cloudpos.notification.dto.SmsResponseDTO;
import com.cloudpos.notification.entity.SmsLog;
import com.cloudpos.notification.mapper.NotificationMapper;
import com.cloudpos.notification.notification.NotificationStatus;
import com.cloudpos.notification.repository.SmsLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

    private final SmsLogRepository smsLogRepository;
    private final NotificationMapper notificationMapper;

    public SmsServiceImpl(SmsLogRepository smsLogRepository, NotificationMapper notificationMapper) {
        this.smsLogRepository = smsLogRepository;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public SmsResponseDTO sendSms(SendSmsRequestDTO request) {
        SmsLog smsLog = new SmsLog();
        smsLog.setNotificationId(request.getNotificationId());
        smsLog.setPhoneNumber(request.getPhoneNumber());
        smsLog.setMessage(request.getMessage());
        smsLog.setStatus(NotificationStatus.PENDING);
        smsLog.setProviderResponse("SMS provider integration prepared: Twilio, Vonage, AWS SNS");

        SmsLog saved = smsLogRepository.save(smsLog);
        log.info("SMS queued for future provider delivery to {}", request.getPhoneNumber());
        return notificationMapper.toSmsResponse(saved);
    }
}
