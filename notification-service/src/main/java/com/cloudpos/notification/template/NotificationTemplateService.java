package com.cloudpos.notification.template;

import java.util.Map;

public interface NotificationTemplateService {

    String render(String templateName, Map<String, Object> variables);
}
