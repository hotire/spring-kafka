package com.github.hotire.springkafka.core.produce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.internals.FutureRecordMetadata;
import org.apache.kafka.clients.producer.internals.ProducerBatch;
import org.apache.kafka.common.header.Header;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProducerBatchWrapper {
    private final ProducerBatch producerBatch;

    public boolean isFull() {
        return producerBatch.isFull();
    }

    public FutureRecordMetadata tryAppend(long timestamp, byte[] key, byte[] value, Header[] headers, Callback callback, long now) {
        return producerBatch.tryAppend(timestamp, key, value, headers, callback, now);
    }
}
