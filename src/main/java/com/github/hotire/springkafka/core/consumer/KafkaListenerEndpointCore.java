package com.github.hotire.springkafka.core.consumer;

import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.converter.MessageConverter;

/**
 * @see KafkaListenerEndpoint
 * @see org.springframework.kafka.config.KafkaListenerEndpointAdapter
 * @see org.springframework.kafka.config.AbstractKafkaListenerEndpoint
 * @see org.springframework.kafka.config.MethodKafkaListenerEndpoint
 */
interface KafkaListenerEndpointCore {

    /**
     * @see KafkaListenerEndpoint#getId()
     */
    String getId();

    /**
     * @see org.springframework.kafka.config.AbstractKafkaListenerEndpoint#setupListenerContainer(MessageListenerContainer, MessageConverter)
     */
    void setupListenerContainer(MessageListenerContainer listenerContainer, MessageConverter messageConverter);
}
