package com.cloudpos.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Map;

public class SendEmailRequestDTO {

    private String notificationId;

    @Email
    @NotBlank
    private String recipientEmail;

    @NotBlank
    @Size(max = 255)
    private String subject;

    @NotBlank
    @Size(max = 8000)
    private String body;

    private boolean html;
    private String templateName;
    private Map<String, Object> templateVariables;

    public SendEmailRequestDTO() {
    }

    public SendEmailRequestDTO(String notificationId, String recipientEmail, String subject,
            String body, boolean html, String templateName, Map<String, Object> templateVariables) {
        this.notificationId = notificationId;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.body = body;
        this.html = html;
        this.templateName = templateName;
        this.templateVariables = templateVariables;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getTemplateVariables() {
        return templateVariables;
    }

    public void setTemplateVariables(Map<String, Object> templateVariables) {
        this.templateVariables = templateVariables;
    }
}
