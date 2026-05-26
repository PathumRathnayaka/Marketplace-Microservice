package com.cloudpos.notification.controller;

import com.cloudpos.common.response.ApiResponse;
import com.cloudpos.notification.dto.EmailResponseDTO;
import com.cloudpos.notification.dto.SendEmailRequestDTO;
import com.cloudpos.notification.email.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<EmailResponseDTO>> send(@Valid @RequestBody SendEmailRequestDTO request) {
        EmailResponseDTO response = request.isHtml()
                ? emailService.sendHtmlEmail(request)
                : emailService.sendPlainEmail(request);
        return ResponseEntity.ok(ApiResponse.success("Email sent", response));
    }

    @PostMapping("/send-template")
    public ResponseEntity<ApiResponse<EmailResponseDTO>> sendTemplate(@Valid @RequestBody SendEmailRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Template email sent", emailService.sendTemplateEmail(request)));
    }
}
