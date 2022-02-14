package com.github.hotire.springkafka.record_accumulator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentMap;

import org.apache.kafka.clients.producer.internals.ProducerBatch;
import org.apache.kafka.common.TopicPartition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @see org.apache.kafka.clients.producer.internals.RecordAccumulator
 */
@RequiredArgsConstructor
@Getter
public class CustomRecordAccumulator {
    private final ConcurrentMap<TopicPartition, Deque<ProducerBatch>> batches;

    /**
     * @see org.apache.kafka.clients.producer.internals.RecordAccumulator#getOrCreateDeque(TopicPartition)
     */
    public Deque<ProducerBatch> getOrCreateDeque(TopicPartition tp) {
        Deque<ProducerBatch> d = this.batches.get(tp);
        if (d != null) {
            return d;
        }
        d = new ArrayDeque<>();
        Deque<ProducerBatch> previous = this.batches.putIfAbsent(tp, d);
        if (previous == null) {
            return d;
        } else {
            return previous;
        }
    }
}
