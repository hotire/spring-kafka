package com.github.hotire.springkafka.core.consumer.factory;

import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.JavaUtils;
import org.springframework.kafka.support.converter.MessageConverter;

/**
 * @see org.springframework.kafka.config.KafkaListenerContainerFactory
 * @see org.springframework.kafka.config.AbstractKafkaListenerContainerFactory
 * @see org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
 */
public class KafkaListenerContainerFactoryCore<C extends AbstractMessageListenerContainer<K, V>, K, V> {


    private MessageConverter messageConverter;

    /**
     * @see org.springframework.kafka.config.KafkaListenerContainerFactory#createListenerContainer(KafkaListenerEndpoint)
     * @see org.springframework.kafka.config.AbstractKafkaListenerContainerFactory#createListenerContainer(KafkaListenerEndpoint)
     */
    public C createListenerContainer(KafkaListenerEndpoint endpoint) {
        C instance = (C) createContainerInstance(endpoint);
        JavaUtils.INSTANCE
                .acceptIfNotNull(endpoint.getId(), instance::setBeanName);
        endpoint.setupListenerContainer(instance, this.messageConverter);
        return null;
    }

    /**
     * @see org.springframework.kafka.config.AbstractKafkaListenerContainerFactory#createContainerInstance(KafkaListenerEndpoint)
     * @see org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory#createContainerInstance(KafkaListenerEndpoint)
     */
    public <K, V> ConcurrentMessageListenerContainer<K, V> createContainerInstance(KafkaListenerEndpoint endpoint) {
        return null;
    }
}
