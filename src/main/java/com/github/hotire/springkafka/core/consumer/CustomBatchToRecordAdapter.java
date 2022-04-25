package com.github.hotire.springkafka.core.consumer;

import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.BatchToRecordAdapter;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;

/**
 * @see BatchToRecordAdapter
 * @see org.springframework.kafka.listener.adapter.DefaultBatchToRecordAdapter
 */
public class CustomBatchToRecordAdapter<K,V> implements BatchToRecordAdapter<K,V> {
    @Override
    public void adapt(List<Message<?>> messages, List<ConsumerRecord<K, V>> consumerRecords, Acknowledgment ack, Consumer<?, ?> consumer, Callback<K, V> callback) {

    }
}
