package com.github.hotire.springkafka.core.consumer;

import org.apache.kafka.clients.consumer.internals.AbstractCoordinator;
import org.apache.kafka.clients.consumer.internals.RequestFuture;

/**
 * @see AbstractCoordinator
 */
public class AbstractCoordinatorCore {

    /**
     * @see AbstractCoordinator#maybeLeaveGroup(String)
     */
    public synchronized RequestFuture<Void> maybeLeaveGroup(String leaveReason) {
        return RequestFuture.voidSuccess();
    }
}
