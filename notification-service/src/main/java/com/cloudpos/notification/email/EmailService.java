package com.cloudpos.notification.email;

import com.cloudpos.notification.dto.EmailResponseDTO;
import com.cloudpos.notification.dto.SendEmailRequestDTO;

public interface EmailService {

    EmailResponseDTO sendPlainEmail(SendEmailRequestDTO request);

    EmailResponseDTO sendHtmlEmail(SendEmailRequestDTO request);

    EmailResponseDTO sendTemplateEmail(SendEmailRequestDTO request);
}
