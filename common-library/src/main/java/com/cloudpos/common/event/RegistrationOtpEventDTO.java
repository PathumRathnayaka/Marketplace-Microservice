package com.cloudpos.common.event;

import com.cloudpos.common.enums.VerificationPurpose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class RegistrationOtpEventDTO {
    private String email;
    private String otpCode;
    private VerificationPurpose purpose;
    private LocalDateTime createdAt;

    public RegistrationOtpEventDTO() {
    }

    public RegistrationOtpEventDTO(String email, String otpCode, VerificationPurpose purpose, LocalDateTime createdAt) {
        this.email = email;
        this.otpCode = otpCode;
        this.purpose = purpose;
        this.createdAt = createdAt;
    }

    public static RegistrationOtpEventDTOBuilder builder() {
        return new RegistrationOtpEventDTOBuilder();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public VerificationPurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(VerificationPurpose purpose) {
        this.purpose = purpose;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class RegistrationOtpEventDTOBuilder {
        private String email;
        private String otpCode;
        private VerificationPurpose purpose;
        private LocalDateTime createdAt;

        public RegistrationOtpEventDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RegistrationOtpEventDTOBuilder otpCode(String otpCode) {
            this.otpCode = otpCode;
            return this;
        }

        public RegistrationOtpEventDTOBuilder purpose(VerificationPurpose purpose) {
            this.purpose = purpose;
            return this;
        }

        public RegistrationOtpEventDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public RegistrationOtpEventDTO build() {
            return new RegistrationOtpEventDTO(email, otpCode, purpose, createdAt);
        }
    }
}
