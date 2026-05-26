package com.cloudpos.notification.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "cloudpos.events";

    public static final String ROUTING_LOW_STOCK = "low.stock.created";
    public static final String ROUTING_SUPPLIER_OFFER = "supplier.offer.created";
    public static final String ROUTING_USER_REGISTERED = "user.registered";
    public static final String ROUTING_NOTIFICATION = "notification.send";

    public static final String QUEUE_LOW_STOCK = "cloudpos.low-stock.queue";
    public static final String QUEUE_SUPPLIER_OFFER = "cloudpos.supplier-offer.queue";
    public static final String QUEUE_USER_REGISTERED = "cloudpos.user-registered.queue";
    public static final String QUEUE_NOTIFICATION = "cloudpos.notification.queue";

    @Bean
    public TopicExchange cloudposEventsExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue lowStockQueue() {
        return new Queue(QUEUE_LOW_STOCK, true);
    }

    @Bean
    public Queue supplierOfferQueue() {
        return new Queue(QUEUE_SUPPLIER_OFFER, true);
    }

    @Bean
    public Queue userRegisteredQueue() {
        return new Queue(QUEUE_USER_REGISTERED, true);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE_NOTIFICATION, true);
    }

    @Bean
    public Binding lowStockBinding() {
        return BindingBuilder.bind(lowStockQueue()).to(cloudposEventsExchange()).with(ROUTING_LOW_STOCK);
    }

    @Bean
    public Binding supplierOfferBinding() {
        return BindingBuilder.bind(supplierOfferQueue()).to(cloudposEventsExchange()).with(ROUTING_SUPPLIER_OFFER);
    }

    @Bean
    public Binding userRegisteredBinding() {
        return BindingBuilder.bind(userRegisteredQueue()).to(cloudposEventsExchange()).with(ROUTING_USER_REGISTERED);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue()).to(cloudposEventsExchange()).with(ROUTING_NOTIFICATION);
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);
        return factory;
    }
}
