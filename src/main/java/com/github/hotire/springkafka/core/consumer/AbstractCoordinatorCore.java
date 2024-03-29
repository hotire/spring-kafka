package com.github.hotire.springkafka.core.consumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.internals.AbstractCoordinator;
import org.apache.kafka.clients.consumer.internals.Heartbeat;
import org.apache.kafka.clients.consumer.internals.RequestFuture;
import org.apache.kafka.common.utils.KafkaThread;
import org.apache.kafka.common.utils.Timer;

/**
 * @see AbstractCoordinator
 */
@RequiredArgsConstructor
public class AbstractCoordinatorCore {

    /**
     * @see AbstractCoordinator#heartbeat
     */
    private final Heartbeat heartbeat;

    /**
     * @see AbstractCoordinator#pollHeartbeat(long)
     */
    protected synchronized void pollHeartbeat(long now) {
        heartbeat.poll(now);
    }

    /**
     * @see AbstractCoordinator.HeartbeatThread
     */
    private class HeartbeatThread extends KafkaThread {

        public HeartbeatThread(String name, boolean daemon) {
            super(name, daemon);
        }

        /**
         * @see AbstractCoordinator.HeartbeatThread#run()
         */
        public void run() {
            final String leaveReason = "consumer poll timeout has expired. This means the time between subsequent calls to poll() " +
                "was longer than the configured max.poll.interval.ms, which typically implies that " +
                "the poll loop is spending too much time processing messages. " +
                "You can address this either by increasing max.poll.interval.ms or by reducing " +
                "the maximum size of batches returned in poll() with max.poll.records.";
            maybeLeaveGroup(leaveReason);
        }

        /**
         * @see org.apache.kafka.clients.consumer.internals.Heartbeat#pollTimeoutExpired(long) 
         */
        boolean pollTimeoutExpired(long now) {
           return false;
        }
    }

    /**
     * @see AbstractCoordinator#ensureActiveGroup()
     */
    boolean ensureActiveGroup(final Timer timer) {
        // always ensure that the coordinator is ready because we may have been disconnected
        // when sending heartbeats and does not necessarily require us to rejoin the group.
        startHeartbeatThreadIfNeeded();
        return true;
    }

    /**
     * @see AbstractCoordinator#startHeartbeatThreadIfNeeded()
     */
    private synchronized void startHeartbeatThreadIfNeeded() {
        new AbstractCoordinatorCore.HeartbeatThread("", true).start();
    }

    /**
     * @see AbstractCoordinator#maybeLeaveGroup(String)
     */
    public synchronized RequestFuture<Void> maybeLeaveGroup(String leaveReason) {
        return RequestFuture.voidSuccess();
    }
}
