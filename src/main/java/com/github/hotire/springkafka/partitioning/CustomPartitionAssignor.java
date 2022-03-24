package com.github.hotire.springkafka.partitioning;

import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.consumer.internals.PartitionAssignor;
import org.apache.kafka.common.Cluster;

/**
 * @see org.apache.kafka.clients.consumer.ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG
 * @see org.apache.kafka.clients.consumer.internals.AbstractPartitionAssignor
 * @see org.apache.kafka.clients.consumer.RangeAssignor
 * @see org.apache.kafka.clients.consumer.RoundRobinAssignor
 * @see org.apache.kafka.clients.consumer.StickyAssignor
 */
public class CustomPartitionAssignor implements PartitionAssignor {

    @Override
    public Subscription subscription(Set<String> topics) {
        return null;
    }

    @Override
    public Map<String, Assignment> assign(Cluster metadata, Map<String, Subscription> subscriptions) {
        return null;
    }

    @Override
    public void onAssignment(Assignment assignment) {

    }

    @Override
    public void onAssignment(Assignment assignment, int generation) {

    }

    @Override
    public String name() {
        return null;
    }
}
