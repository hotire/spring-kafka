package com.github.hotire.springkafka.interview;

import java.util.List;
import java.util.Set;

import org.apache.kafka.clients.producer.internals.ProducerBatch;
import org.apache.kafka.common.Cluster;

/**
 * @see org.apache.kafka.clients.producer.internals.Sender
 */
public class SenderCore {

    /**
     * @see org.apache.kafka.clients.producer.internals.Sender#sendProducerData(long)
     * @see org.apache.kafka.clients.producer.internals.RecordAccumulator#drain(Cluster, Set, int, long)
     */
    private long sendProducerData(long now) {
        return now;
    }

    /**
     * @see org.apache.kafka.clients.producer.internals.Sender#sendProduceRequest(long, int, short, int, List)
     */
    private void sendProduceRequest(long now, int destination, short acks, int timeout, List<ProducerBatch> batches) {

    }
}
