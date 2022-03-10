package com.github.hotire.springkafka.interview.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Cluster;

import lombok.RequiredArgsConstructor;

/**
 * @see KafkaProducer#doSend(ProducerRecord, Callback)
 */
@RequiredArgsConstructor
public class KafkaProducerCore<K, V> {

    private final Partitioner partitioner;

    /**
     * @see KafkaProducer#partition(ProducerRecord, byte[], byte[], Cluster)
     */
    private int partition(ProducerRecord<K, V> record, byte[] serializedKey, byte[] serializedValue, Cluster cluster) {
        Integer partition = record.partition();
        return partition != null ?
               partition :
               partitioner.partition(
                       record.topic(), record.key(), serializedKey, record.value(), serializedValue, cluster);
    }
}
