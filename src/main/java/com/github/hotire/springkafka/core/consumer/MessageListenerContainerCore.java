package com.github.hotire.springkafka.core.consumer;

import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * @see MessageListenerContainer
 * @see AbstractMessageListenerContainer
 * @see ConcurrentMessageListenerContainer
 */
interface MessageListenerContainerCore {

    /**
     * @see MessageListenerContainer#setupMessageListener(Object)
     * @see AbstractMessageListenerContainer#setupMessageListener(Object)
     */
    void setupMessageListener(Object messageListener);
}
