package com.cloudpos.notification.config;

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

/**
 * RabbitMQ topology configuration for notification-service.
 *
 * Exchange: cloudpos.events (Topic Exchange)
 * Queues:
 * cloudpos.low-stock.queue → low.stock.created
 * cloudpos.supplier-offer.queue → supplier.offer.created
 * cloudpos.user-registered.queue → user.registered
 * cloudpos.notification.queue → notification.send
 */
@Configuration
public class RabbitMQConfig {

    // ─── Exchange ──────────────────────────────────────────────────────────────
    public static final String EXCHANGE = "cloudpos.events";

    // ─── Routing Keys ─────────────────────────────────────────────────────────
    public static final String ROUTING_LOW_STOCK = "low.stock.created";
    public static final String ROUTING_SUPPLIER_OFFER = "supplier.offer.created";
    public static final String ROUTING_USER_REGISTERED = "user.registered";
    public static final String ROUTING_NOTIFICATION = "notification.send";

    // ─── Queue Names ──────────────────────────────────────────────────────────
    public static final String QUEUE_LOW_STOCK = "cloudpos.low-stock.queue";
    public static final String QUEUE_SUPPLIER_OFFER = "cloudpos.supplier-offer.queue";
    public static final String QUEUE_USER_REGISTERED = "cloudpos.user-registered.queue";
    public static final String QUEUE_NOTIFICATION = "cloudpos.notification.queue";

    // ─── Exchange Bean ─────────────────────────────────────────────────────────
    @Bean
    public TopicExchange cloudposEventsExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    // ─── Queue Beans ──────────────────────────────────────────────────────────
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

    // ─── Binding Beans ────────────────────────────────────────────────────────
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

    // ─── JSON Message Converter ───────────────────────────────────────────────
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

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}
