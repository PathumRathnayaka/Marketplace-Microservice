package com.cloudpos.notification.template;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    @Override
    public String render(String templateName, Map<String, Object> variables) {
        String template = resolveTemplate(templateName);
        if (variables == null || variables.isEmpty()) {
            return template;
        }
        String rendered = template;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            rendered = rendered.replace("{{" + entry.getKey() + "}}", String.valueOf(entry.getValue()));
        }
        return rendered;
    }

    private String resolveTemplate(String templateName) {
        if ("low-stock-alert".equalsIgnoreCase(templateName)) {
            return "Low stock alert for {{productName}}. Required quantity: {{requiredQuantity}}.";
        }
        if ("welcome-email".equalsIgnoreCase(templateName)) {
            return "Welcome to Cloud POS, {{name}}.";
        }
        if ("supplier-offer".equalsIgnoreCase(templateName)) {
            return "New supplier offer received from {{supplierName}}.";
        }
        if ("password-reset".equalsIgnoreCase(templateName)) {
            return "Use this password reset code: {{resetCode}}.";
        }
        return "{{message}}";
    }
}
