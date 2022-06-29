package com.github.hotire.springkafka.core.consumer;

import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.listener.MessageListenerContainer;

/**
 * @see org.springframework.kafka.config.KafkaListenerEndpointRegistry
 */
public class KafkaListenerEndpointRegistryCore {

    /**
     * @see org.springframework.kafka.config.KafkaListenerEndpointRegistry#registerListenerContainer(KafkaListenerEndpoint, KafkaListenerContainerFactory, boolean)
     */
    public void registerListenerContainer(KafkaListenerEndpoint endpoint, KafkaListenerContainerFactory<?> factory, boolean startImmediately) {

    }

    /**
     * @see org.springframework.kafka.config.KafkaListenerEndpointRegistry#createListenerContainer(KafkaListenerEndpoint, KafkaListenerContainerFactory)
     */
    protected MessageListenerContainer createListenerContainer(KafkaListenerEndpoint endpoint,
                                                               KafkaListenerContainerFactory<?> factory) {
        return null;
    }
}
