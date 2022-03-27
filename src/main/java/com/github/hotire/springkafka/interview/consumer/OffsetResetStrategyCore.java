package com.github.hotire.springkafka.interview.consumer;

import org.apache.kafka.clients.consumer.OffsetResetStrategy;

/**
 * @see OffsetResetStrategy
 * @see org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG
 */
public enum  OffsetResetStrategyCore {
    LATEST, EARLIEST, NONE
}
