package com.github.hotire.springkafka.interview.consumer;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.utils.Timer;

/**
 * @see org.apache.kafka.clients.consumer.Consumer
 * @see KafkaConsumer
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


    /**
     * @see KafkaConsumer#pollForFetches(Timer) 
     */
    private Map<TopicPartition, List<ConsumerRecord<K, V>>> pollForFetches(Timer timer) {
        return Collections.emptyMap();
    }

}
