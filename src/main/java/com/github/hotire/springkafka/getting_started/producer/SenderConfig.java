package com.github.hotire.springkafka.getting_started.producer;

import java.util.Map;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SenderConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public Map<String, Object> producerConfigs() {
        return kafkaProperties.buildProducerProperties();
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public Sender sender() {
        return new Sender(kafkaTemplate());
    }
}
