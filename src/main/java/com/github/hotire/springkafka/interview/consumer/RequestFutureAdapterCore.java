package com.github.hotire.springkafka.interview.consumer;

import org.apache.kafka.clients.consumer.internals.RequestFuture;
import org.apache.kafka.clients.consumer.internals.RequestFutureAdapter;

/**
 * @see org.apache.kafka.clients.consumer.internals.RequestFutureAdapter
 * @see RequestFuture#compose(RequestFutureAdapter)
 *
 * - Rebalance
 * @see org.apache.kafka.clients.consumer.internals.AbstractCoordinator.JoinGroupResponseHandler
 * @see org.apache.kafka.clients.consumer.internals.AbstractCoordinator.SyncGroupResponseHandler
 *
 * - Offst Init
 * @see org.apache.kafka.clients.consumer.internals.ConsumerCoordinator.OffsetFetchResponseHandler
 *
 * - Offset commit
 * @see org.apache.kafka.clients.consumer.internals.ConsumerCoordinator.OffsetCommitResponseHandler
 *
 * - Heartbeat
 * @see org.apache.kafka.clients.consumer.internals.ConsumerCoordinator.HeartbeatResponseHandler
 *
 */
public class RequestFutureAdapterCore<F, T> extends RequestFutureAdapter<F, T> {

    @Override
    public void onSuccess(F value, RequestFuture<T> future) {
        
    }
}
