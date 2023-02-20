package com.github.hotire.springkafka.core.consumer;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.internals.Fetcher;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.record.Record;
import org.apache.kafka.common.record.RecordBatch;
import org.apache.kafka.common.record.TimestampType;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.utils.CloseableIterator;
import org.apache.kafka.common.utils.Utils;

/**
 * @see Fetcher
 * @see Fetcher.CompletedFetch
 */
@RequiredArgsConstructor
public class CompletedFetchCore<K, V> {

    private final Deserializer<K> keyDeserializer;
    private final Deserializer<V> valueDeserializer;

    /**
     * @see Fetcher.CompletedFetch#isConsumed
     */
    private boolean isConsumed = false;

    /**
     * @see Fetcher.CompletedFetch#initialized
     */
    private boolean initialized = false;

    /**
     * @see Fetcher.CompletedFetch#records
     */
    private CloseableIterator<Record> records;


    /**
     * @see Fetcher.CompletedFetch#drain()
     */
    public void drain() {

    }

    /**
     * @see Fetcher.CompletedFetch#nextFetchedRecord()
     */
    public Record nextFetchedRecord() {
        return records.next();
    }

    /**
     * @see Fetcher.CompletedFetch#fetchRecords(int) 
     */
    private List<ConsumerRecord<K, V>> fetchRecords(int maxRecords) {
        return Collections.emptyList();
    }

    /**
     * @see Fetcher.CompletedFetch#parseRecord(TopicPartition, RecordBatch, Record) 
     */
    private ConsumerRecord<K, V> parseRecord(TopicPartition partition,
        RecordBatch batch,
        Record record) {
        long offset = record.offset();
        long timestamp = record.timestamp();
        Optional<Integer> leaderEpoch = Optional.empty();
        TimestampType timestampType = batch.timestampType();
        Headers headers = new RecordHeaders(record.headers());
        ByteBuffer keyBytes = record.key();
        byte[] keyByteArray = keyBytes == null ? null : Utils.toArray(keyBytes);
        K key = keyBytes == null ? null : this.keyDeserializer.deserialize(partition.topic(), headers, keyByteArray);
        ByteBuffer valueBytes = record.value();
        byte[] valueByteArray = valueBytes == null ? null : Utils.toArray(valueBytes);
        V value = valueBytes == null ? null : this.valueDeserializer.deserialize(partition.topic(), headers, valueByteArray);
        return new ConsumerRecord<>(partition.topic(), partition.partition(), offset,
            timestamp, timestampType, record.checksumOrNull(),
            keyByteArray == null ? ConsumerRecord.NULL_SIZE : keyByteArray.length,
            valueByteArray == null ? ConsumerRecord.NULL_SIZE : valueByteArray.length,
            key, value, headers, leaderEpoch);
    }
}
