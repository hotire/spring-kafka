package com.github.hotire.springkafka.core.consumer;

import org.apache.kafka.clients.consumer.internals.AbstractCoordinator;
import org.apache.kafka.clients.consumer.internals.RequestFuture;

/**
 * @see AbstractCoordinator
 */
public class AbstractCoordinatorCore {

    private class HeartbeatThread  {

        public void run() {
            final String leaveReason = "consumer poll timeout has expired. This means the time between subsequent calls to poll() " +
                "was longer than the configured max.poll.interval.ms, which typically implies that " +
                "the poll loop is spending too much time processing messages. " +
                "You can address this either by increasing max.poll.interval.ms or by reducing " +
                "the maximum size of batches returned in poll() with max.poll.records.";
            maybeLeaveGroup(leaveReason);
        }
    }

    /**
     * @see AbstractCoordinator#maybeLeaveGroup(String)
     */
    public synchronized RequestFuture<Void> maybeLeaveGroup(String leaveReason) {
        return RequestFuture.voidSuccess();
    }
}
