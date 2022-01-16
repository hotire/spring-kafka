package com.github.hotire.springkafka.getting_started.consumer;

import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.FixedBackOff;

import com.github.hotire.springkafka.getting_started.SkippableException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ReceiverConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public Map<String, Object> consumerConfigs() {
        return kafkaProperties.buildConsumerProperties();
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        ContainerProperties props = factory.getContainerProperties();
        props.setAckMode(AckMode.MANUAL_IMMEDIATE);
        factory.setConsumerFactory(consumerFactory());
//        factory.setErrorHandler((error, data) -> log.error("error : {}, data : {}", error.getMessage(), data, error));
        factory.setErrorHandler(new SeekToCurrentErrorHandler(new BiConsumer<ConsumerRecord<?, ?>, Exception>() {
            @Override
            public void accept(ConsumerRecord<?, ?> consumerRecord, Exception e) {
                log.error(e.getMessage(), e);
            }
        }, new FixedBackOff(0, 10)));
        factory.setRecoveryCallback((RecoveryCallback<Exception>) retryContext -> {
            KafkaException kafkaException = (KafkaException) retryContext.getLastThrowable();
            if (kafkaException.contains(SkippableException.class)) {
                retryContext.getAttribute(RetryingMessageListenerAdapter.CONTEXT_RECORD);
                Object ack = retryContext.getAttribute(RetryingMessageListenerAdapter.CONTEXT_ACKNOWLEDGMENT);
                if (ack instanceof Acknowledgment) {
                    ((Acknowledgment) ack).acknowledge();
                }
                return kafkaException;
            }
            throw kafkaException;
        });
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000L);
        backOffPolicy.setMaxInterval(1000L);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(2));
        factory.setRetryTemplate(retryTemplate);
        return factory;
    }

    @Bean
    public Receiver receiver() {
        return new Receiver();
    }

}
