package com.github.hotire.springkafka.core.produce;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.FutureRecordMetadata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FutureRecordMetadataDecorator implements Future<RecordMetadata> {

    @Getter
    private final FutureRecordMetadata delegate;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return getDelegate().cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return getDelegate().isCancelled();
    }

    @Override
    public boolean isDone() {
        return getDelegate().isDone();
    }

    @Override
    public RecordMetadata get() throws InterruptedException, ExecutionException {
        return getDelegate().get();
    }

    @Override
    public RecordMetadata get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return getDelegate().get(timeout, unit);
    }
}
