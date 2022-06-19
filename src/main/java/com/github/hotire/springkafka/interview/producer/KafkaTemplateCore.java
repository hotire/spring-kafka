package com.github.hotire.springkafka.interview.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

/**
 * @see org.springframework.kafka.core.KafkaTemplate
 */
public class KafkaTemplateCore<K, V> {

    /**
     * @see org.springframework.kafka.core.KafkaTemplate#send(String, Object, Object)
     */
    public ListenableFuture<SendResult<K, V>> send(String topic, K key, V data) {
        ProducerRecord<K, V> producerRecord = new ProducerRecord<>(topic, key, data);
        return new SettableListenableFuture();
    }
}
