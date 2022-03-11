package com.github.hotire.springkafka.interview.consumer;

import java.time.Duration;
import java.util.Collection;

import org.apache.kafka.clients.consumer.ConsumerRecords;

/**
 * @see org.apache.kafka.clients.consumer.Consumer
 * @see org.apache.kafka.clients.consumer.KafkaConsumer
 */
public class KafkaConsumerCore<K, V> {

    /**
     * @see org.apache.kafka.clients.consumer.Consumer#subscribe(Collection)
     * @see org.apache.kafka.clients.consumer.KafkaConsumer#subscribe(Collection)
     */
    void subscribe(Collection<String> topics) {

    }

    /**
     * @see org.apache.kafka.clients.consumer.Consumer#poll(Duration)
     * @see org.apache.kafka.clients.consumer.KafkaConsumer#poll(Duration)
     */
    ConsumerRecords<K, V> poll(Duration timeout) {
        return null;
    }

}
