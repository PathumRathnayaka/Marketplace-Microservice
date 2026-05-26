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

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    private final SmsLogRepository smsLogRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public SmsResponseDTO sendSms(SendSmsRequestDTO request) {
        SmsLog smsLog = SmsLog.builder()
                .notificationId(request.getNotificationId())
                .phoneNumber(request.getPhoneNumber())
                .message(request.getMessage())
                .status(NotificationStatus.PENDING)
                .providerResponse("SMS provider integration prepared: Twilio, Vonage, AWS SNS")
                .build();
        SmsLog saved = smsLogRepository.save(smsLog);
        log.info("SMS queued for future provider delivery to {}", request.getPhoneNumber());
        return notificationMapper.toSmsResponse(saved);
    }
}
