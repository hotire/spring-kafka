package com.github.hotire.springkafka.core.produce;

import org.apache.kafka.clients.producer.internals.ProducerBatch;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProducerBatchWrapper {
    private final ProducerBatch producerBatch;

    public boolean isFull() {
        return producerBatch.isFull();
    }
}
