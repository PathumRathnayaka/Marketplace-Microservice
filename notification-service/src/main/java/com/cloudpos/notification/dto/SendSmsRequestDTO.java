package com.cloudpos.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SendSmsRequestDTO {

    private String notificationId;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Size(max = 2000)
    private String message;

    public SendSmsRequestDTO() {
    }

    public SendSmsRequestDTO(String notificationId, String phoneNumber, String message) {
        this.notificationId = notificationId;
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
