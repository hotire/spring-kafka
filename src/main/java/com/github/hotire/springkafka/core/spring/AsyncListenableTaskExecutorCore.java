package com.github.hotire.springkafka.core.spring;

import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;


/**
 * @see AsyncListenableTaskExecutor
 * @see SimpleAsyncTaskExecutor
 */
public interface AsyncListenableTaskExecutorCore {

    /**
     * @see KafkaMessageListenerContainer#doStart()
     * @see AsyncListenableTaskExecutor#submitListenable(Runnable)
     */
    ListenableFuture<?> submitListenable(Runnable task);
}
