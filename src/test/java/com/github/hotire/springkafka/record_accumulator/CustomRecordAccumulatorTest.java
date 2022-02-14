package com.github.hotire.springkafka.record_accumulator;

import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;

class CustomRecordAccumulatorTest {

    @Test
    void getBatches() {
        // given
        final TopicPartition topicPartition = new TopicPartition("hotire", 0);
        final CustomRecordAccumulator accumulator = new CustomRecordAccumulator(new ConcurrentHashMap<>());

        accumulator.getBatches().put(topicPartition, new ArrayDeque<>());

        accumulator.getBatches().get(topicPartition).getFirst();
        accumulator.getBatches().get(topicPartition).getLast();
    }
}