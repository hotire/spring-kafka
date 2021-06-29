package com.github.hotire.springkafka.core.produce;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.internals.ProducerBatch;
import org.apache.kafka.clients.producer.internals.RecordAccumulator;
import org.apache.kafka.clients.producer.internals.RecordAccumulator.RecordAppendResult;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.Node;
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

    public Map<Integer, List<ProducerBatch>> drain(Cluster cluster, Set<Node> nodes, int maxSize, long now) {
        return recordAccumulator.drain(cluster, nodes, maxSize, now);
    }
}
