package com.cloudpos.pos.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ configuration for pos-service (producer side).
 *
 * pos-service publishes events to the cloudpos.events Topic Exchange.
 * The exchange and queues are declared by notification-service.
 * This config only wires up the template and message converter.
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "cloudpos.events";
    public static final String ROUTING_LOW_STOCK = "low.stock.created";

    @Bean
    public TopicExchange cloudposEventsExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
