package com.github.hotire.springkafka.core.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;

/**
 * @see org.springframework.kafka.listener.MessageListener
 * @see org.springframework.kafka.listener.AcknowledgingConsumerAwareMessageListener
 * @see org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter
 * @see org.springframework.kafka.listener.adapter.RecordMessagingMessageListenerAdapter
 */
public class MessageListenerCore<K, V> {

    /**
     * @see org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter#onMessage(ConsumerRecord, Acknowledgment, Consumer)
     */
    public void onMessage(ConsumerRecord<K, V> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {

    }

    /**
     * @see org.springframework.kafka.listener.adapter.RecordMessagingMessageListenerAdapter#invokeHandler(Object, Acknowledgment, Message, Consumer)
     */
    protected final Object invokeHandler(Object data, Acknowledgment acknowledgment, Message<?> message,
                                         Consumer<?, ?> consumer) {
        return data;
    }
}
