package com.github.hotire.springkafka.interview.consumer;

import java.nio.ByteBuffer;

import org.apache.kafka.clients.consumer.internals.AbstractCoordinator;
import org.apache.kafka.clients.consumer.internals.RequestFuture;

/**
 * @see org.apache.kafka.clients.consumer.internals.ConsumerCoordinator
 */
public class ConsumerCoordinatorCore {

    /**
     * @see AbstractCoordinator#initiateJoinGroup()
     */
    private synchronized RequestFuture<ByteBuffer> initiateJoinGroup() {
        disableHeartbeatThread();
        return new RequestFutureCore<>();
    }

    /**
     * @see AbstractCoordinator#disableHeartbeatThread()
     */
    private synchronized void disableHeartbeatThread() {

    }
}
