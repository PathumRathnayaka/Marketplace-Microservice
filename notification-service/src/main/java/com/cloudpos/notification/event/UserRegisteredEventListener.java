package com.cloudpos.notification.event;

import com.cloudpos.common.event.UserRegisteredEventDTO;
import com.cloudpos.notification.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumes user-registered events published by auth-service.
 * Routing key: user.registered
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisteredEventListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_USER_REGISTERED)
    public void onUserRegisteredEvent(UserRegisteredEventDTO event) {
        log.info("[USER-REGISTERED EVENT] userId={} email={} role={}",
                event.getUserId(), event.getEmail(), event.getRole());
        // TODO: send welcome email
        // notificationService.sendWelcomeEmail(event);
    }
}
