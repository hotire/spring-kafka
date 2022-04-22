package com.github.hotire.springkafka.core.produce;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.kafka.clients.consumer.ConsumerGroupMetadata;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.ProducerFencedException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaProducerDecorator<K, V> implements Producer<K, V> {

    @Getter
    private final Producer<K, V> delegate;

    @Override
    public void initTransactions() {
        getDelegate().initTransactions();
    }

    @Override
    public void beginTransaction() throws ProducerFencedException {
        getDelegate().beginTransaction();
    }

    @Override
    public void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets, String consumerGroupId) throws ProducerFencedException {
        getDelegate().sendOffsetsToTransaction(offsets, consumerGroupId);
    }

    @Override
    public void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> map, ConsumerGroupMetadata consumerGroupMetadata) throws ProducerFencedException {
        
    }

    @Override
    public void commitTransaction() throws ProducerFencedException {
        getDelegate().commitTransaction();
    }

    @Override
    public void abortTransaction() throws ProducerFencedException {
        getDelegate().abortTransaction();
    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord<K, V> record) {
        return getDelegate().send(record);
    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord<K, V> record, Callback callback) {
        return getDelegate().send(record, callback);
    }

    @Override
    public void flush() {
        getDelegate().flush();
    }

    @Override
    public List<PartitionInfo> partitionsFor(String topic) {
        return getDelegate().partitionsFor(topic);
    }

    @Override
    public Map<MetricName, ? extends Metric> metrics() {
        return getDelegate().metrics();
    }

    @Override
    public void close() {
        getDelegate().close();
    }

    @Override
    public void close(Duration timeout) {
        getDelegate().close(timeout);
    }
}
