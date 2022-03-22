package com.github.hotire.springkafka.interview.consumer;

import org.apache.kafka.clients.consumer.internals.RequestFuture;
import org.apache.kafka.clients.consumer.internals.RequestFutureAdapter;

/**
 * @see org.apache.kafka.clients.consumer.internals.RequestFutureAdapter
 * @see RequestFuture#compose(RequestFutureAdapter)
 */
public class RequestFutureAdapterCore<F, T> extends RequestFutureAdapter<F, T> {

    @Override
    public void onSuccess(F value, RequestFuture<T> future) {
        
    }
}
