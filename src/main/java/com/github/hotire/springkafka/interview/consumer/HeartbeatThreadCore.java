package com.github.hotire.springkafka.interview.consumer;

import org.apache.kafka.common.utils.KafkaThread;

/**
 * @see org.apache.kafka.clients.consumer.internals.AbstractCoordinator.HeartbeatThread
 * @see org.apache.kafka.common.utils.KafkaThread
 * @see org.apache.kafka.clients.consumer.ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG
 * @see org.apache.kafka.clients.consumer.internals.Heartbeat
 */
public class HeartbeatThreadCore extends KafkaThread {
    public static final String HEARTBEAT_THREAD_PREFIX = "kafka-coordinator-heartbeat-thread";

    public HeartbeatThreadCore(String groupId) {
        super(HEARTBEAT_THREAD_PREFIX + (groupId.isEmpty() ? "" : " | " + groupId), true);
    }

    /**
     * @see org.apache.kafka.clients.consumer.internals.AbstractCoordinator.HeartbeatThread#enable()
     */
    public void enable() {

    }

    /**
     * @see org.apache.kafka.clients.consumer.internals.AbstractCoordinator.HeartbeatThread#disable()
     */
    public void disable() {

    }
}
