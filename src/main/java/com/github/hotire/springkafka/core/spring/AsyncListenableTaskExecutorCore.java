package com.github.hotire.springkafka.core.spring;

import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.util.concurrent.ListenableFuture;

public interface AsyncListenableTaskExecutorCore {

    /**
     * @see  KafkaMessageListenerContainer#doStart()
     */
    ListenableFuture<?> submitListenable(Runnable task);
}
