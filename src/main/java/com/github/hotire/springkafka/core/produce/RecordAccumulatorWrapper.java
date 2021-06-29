package com.github.hotire.springkafka.core.produce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.internals.RecordAccumulator;
import org.apache.kafka.clients.producer.internals.RecordAccumulator.RecordAppendResult;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;

public class RecordAccumulatorWrapper {
    private RecordAccumulator recordAccumulator;

    public RecordAppendResult append(TopicPartition tp,
                                     long timestamp,
                                     byte[] key,
                                     byte[] value,
                                     Header[] headers,
                                     Callback callback,
                                     long maxTimeToBlock) throws InterruptedException {
        return recordAccumulator.append(tp, timestamp, key, value, headers, callback, maxTimeToBlock);
    }
}
