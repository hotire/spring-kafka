package com.github.hotire.springkafka.core.consumer.factory;

import java.util.HashMap;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

class KafkaListenerContainerFactoryCoreTest {


    @Test
    void create() {
        // when
        final String topic = "topic";
        final ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory(new HashMap<>());
        final ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        final ConcurrentMessageListenerContainer<String, String> container  = factory.createContainer(topic);

        container.setupMessageListener(new MessageListener<String, String>(){
            @Override
            public void onMessage(ConsumerRecord<String, String> data) {

            }
        });
    }
}