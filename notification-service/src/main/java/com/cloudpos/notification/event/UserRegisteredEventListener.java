package com.cloudpos.notification.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserRegisteredEventListener {

    public void onUserRegisteredEvent(Object event) {
        log.info("User registered event listener prepared for RabbitMQ integration");
    }
}
