package com.github.hotire.springkafka.core.consumer;

import org.springframework.kafka.annotation.KafkaListener;

/**
 * @see org.springframework.kafka.annotation.KafkaListener
 */
public interface KafkaListenerCore {

    /**
     * @see KafkaListener#containerFactory()
     */
    String containerFactory();


}
