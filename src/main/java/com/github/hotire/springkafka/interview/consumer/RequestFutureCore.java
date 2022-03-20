package com.github.hotire.springkafka.interview.consumer;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.kafka.clients.consumer.internals.RequestFuture;
import org.apache.kafka.clients.consumer.internals.RequestFutureAdapter;
import org.apache.kafka.clients.consumer.internals.RequestFutureListener;

/**
 * @see org.apache.kafka.clients.consumer.internals.RequestFuture
 */
public class RequestFutureCore<T> extends RequestFuture<T> {

    private final ConcurrentLinkedQueue<RequestFutureListener<T>> listeners = new ConcurrentLinkedQueue<>();

    /***
     * @see RequestFuture#addListener(RequestFutureListener)
     */
    @Override
    public void addListener(RequestFutureListener<T> listener) {
        super.addListener(listener);
    }

    /**
     * @see RequestFuture#compose(RequestFutureAdapter)
     */
    @Override
    public <S> RequestFuture<S> compose(final RequestFutureAdapter<T, S> adapter) {
        final RequestFuture<S> adapted = new RequestFuture<>();
        addListener(new RequestFutureListener<T>() {
            @Override
            public void onSuccess(T value) {
                adapter.onSuccess(value, adapted);
            }

            @Override
            public void onFailure(RuntimeException e) {
                adapter.onFailure(e, adapted);
            }
        });
        return adapted;
    }

    private void fireSuccess() {
        T value = value();
        while (true) {
            RequestFutureListener<T> listener = listeners.poll();
            if (listener == null) {
                break;
            }
            listener.onSuccess(value);
        }
    }

    private void fireFailure() {
        RuntimeException exception = exception();
        while (true) {
            RequestFutureListener<T> listener = listeners.poll();
            if (listener == null) {
                break;
            }
            listener.onFailure(exception);
        }
    }
}
