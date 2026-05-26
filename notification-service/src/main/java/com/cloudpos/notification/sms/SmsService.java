package com.cloudpos.notification.sms;

import com.cloudpos.notification.dto.SendSmsRequestDTO;
import com.cloudpos.notification.dto.SmsResponseDTO;

public interface SmsService {

    SmsResponseDTO sendSms(SendSmsRequestDTO request);
}
