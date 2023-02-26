package com.github.hotire.springkafka.core.consumer;

import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.listener.ContainerProperties;

/**
 * @see ContainerProperties
 */
public class ContainerPropertiesCore {

    private AsyncListenableTaskExecutor consumerTaskExecutor;

    public AsyncListenableTaskExecutor getConsumerTaskExecutor() {
        return this.consumerTaskExecutor;
    }
}
