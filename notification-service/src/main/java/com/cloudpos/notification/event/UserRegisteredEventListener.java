package com.cloudpos.notification.event;

import com.cloudpos.common.event.UserRegisteredEventDTO;
import com.cloudpos.notification.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumes user-registered events published by auth-service.
 * Routing key: user.registered
 */
@Component
public class UserRegisteredEventListener {

    private static final Logger log = LoggerFactory.getLogger(UserRegisteredEventListener.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_USER_REGISTERED)
    public void onUserRegisteredEvent(UserRegisteredEventDTO event) {
        log.info("[USER-REGISTERED EVENT] userId={} email={} role={}",
                event.getUserId(), event.getEmail(), event.getRole());
        // TODO: send welcome email
        // notificationService.sendWelcomeEmail(event);
    }
}
