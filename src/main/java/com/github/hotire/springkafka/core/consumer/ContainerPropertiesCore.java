package com.github.hotire.springkafka.core.consumer;

import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.listener.ContainerProperties;

/**
 * @see ContainerProperties
 */
public class ContainerPropertiesCore {

    /**
     * @see ContainerProperties#consumerTaskExecutor
     */
    private AsyncListenableTaskExecutor consumerTaskExecutor;

    /**
     * @see ContainerProperties#getConsumerTaskExecutor()
     */
    public AsyncListenableTaskExecutor getConsumerTaskExecutor() {
        return this.consumerTaskExecutor;
    }
}
