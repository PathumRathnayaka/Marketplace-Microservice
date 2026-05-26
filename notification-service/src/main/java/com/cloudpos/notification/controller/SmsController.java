package com.cloudpos.notification.controller;

import com.cloudpos.common.response.ApiResponse;
import com.cloudpos.notification.dto.SendSmsRequestDTO;
import com.cloudpos.notification.dto.SmsResponseDTO;
import com.cloudpos.notification.sms.SmsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<SmsResponseDTO>> send(@Valid @RequestBody SendSmsRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("SMS queued", smsService.sendSms(request)));
    }
}
