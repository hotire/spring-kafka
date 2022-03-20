package com.github.hotire.springkafka.interview.consumer;

import org.apache.kafka.common.utils.KafkaThread;

/**
 * @see org.apache.kafka.clients.consumer.internals.AbstractCoordinator.HeartbeatThread
 * @see org.apache.kafka.common.utils.KafkaThread
 */
public class HeartbeatThreadCore extends KafkaThread {
    public static final String HEARTBEAT_THREAD_PREFIX = "kafka-coordinator-heartbeat-thread";

    public HeartbeatThreadCore(String groupId) {
        super(HEARTBEAT_THREAD_PREFIX + (groupId.isEmpty() ? "" : " | " + groupId), true);
    }
}
