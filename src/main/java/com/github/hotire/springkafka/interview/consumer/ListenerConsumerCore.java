package com.github.hotire.springkafka.interview.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

/**
 * @see KafkaMessageListenerContainer.ListenerConsumer
 */
public class ListenerConsumerCore<K, V> {

    /**
     * @see KafkaMessageListenerContainer.ListenerConsumer#run()
     */
    public void run() { // NOSONAR complexity

    }

    /**
     * @see KafkaMessageListenerContainer.ListenerConsumer#pollAndInvoke()
     */
    protected void pollAndInvoke() {

    }

    /**
     * @see KafkaMessageListenerContainer.ListenerConsumer#doPoll()
     */
    private ConsumerRecords<K, V> doPoll() {
        return ConsumerRecords.empty();
    }

}
