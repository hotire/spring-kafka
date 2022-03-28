package com.github.hotire.springkafka.interview.consumer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.internals.Fetcher;
import org.apache.kafka.common.TopicPartition;

/**
 * @see Fetcher
 * @see org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG
 */
public class FetcherCore<K, V> {

    /**
     * @see Fetcher#fetchedRecords()
     */
    public Map<TopicPartition, List<ConsumerRecord<K, V>>> fetchedRecords() {
        return Collections.emptyMap();
    }
}
