package com.github.hotire.springkafka.interview.producer;

import org.apache.kafka.clients.producer.ProducerConfig;

/**
 * http://kafka.apache.org/documentation.html#producerconfigs
 * @see ProducerConfig
 */
public class ProducerConfigCore {

    /**
     * @see ProducerConfig#BATCH_SIZE_CONFIG
     * DEFAULT 16384
     */
    public static final String BATCH_SIZE_CONFIG = "batch.size";

    /**
     * @see ProducerConfig#LINGER_MS_CONFIG
     * DEFAULT 0
     */
    public static final String LINGER_MS_CONFIG = "linger.ms";

    /**
     * @see ProducerConfig#ACKS_CONFIG
     * DEFAULT 1
     */
    public static final String ACKS_CONFIG = "acks";

}
