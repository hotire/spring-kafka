package com.github.hotire.springkafka.interview.producer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.producer.internals.ProducerBatch;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.Node;

/**
 * @see org.apache.kafka.clients.producer.internals.RecordAccumulator
 */
public class RecordAccumulatorCore {

    /**
     * @see org.apache.kafka.clients.producer.internals.RecordAccumulator#drain(Cluster, Set, int, long)
     */
    public Map<Integer, List<ProducerBatch>> drain(Cluster cluster, Set<Node> nodes, int maxSize, long now) {
        return Collections.emptyMap();
    }
}
