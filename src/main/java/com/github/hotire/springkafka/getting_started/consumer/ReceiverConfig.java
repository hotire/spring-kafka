package com.github.hotire.springkafka.getting_started.consumer;

import com.github.hotire.springkafka.getting_started.CustomKafkaListenerAnnotationBeanPostProcessor;
import com.github.hotire.springkafka.getting_started.SkippableException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient;
import org.apache.kafka.common.utils.SystemTime;
import org.apache.kafka.common.utils.Timer;
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
import org.springframework.kafka.support.KafkaUtils;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ReceiverConfig {

    private final KafkaProperties kafkaProperties;

    private final CustomKafkaListenerAnnotationBeanPostProcessor postProcessor;

    @Bean
    public Map<String, Object> consumerConfigs() {
        final Map<String, Object> consumerConfigs = kafkaProperties.buildConsumerProperties();
        consumerConfigs.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, 3000);
        return consumerConfigs;
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
//        factory.setErrorHandler(new SeekToCurrentErrorHandler(new BiConsumer<ConsumerRecord<?, ?>, Exception>() {
//            @Override
//            public void accept(ConsumerRecord<?, ?> consumerRecord, Exception e) {
//                log.error(e.getMessage(), e);
//            }
//        }, new FixedBackOff(0, 10)));
        factory.setErrorHandler(new SeekToCurrentErrorHandler());
        factory.setRecoveryCallback((RecoveryCallback<Exception>) retryContext -> {
            KafkaException kafkaException = (KafkaException) retryContext.getLastThrowable();
            if (kafkaException.contains(SkippableException.class)) {
                final ConsumerRecord<?, ?> record = (ConsumerRecord<?, ?>) retryContext.getAttribute(RetryingMessageListenerAdapter.CONTEXT_RECORD);
                Object ack = retryContext.getAttribute(RetryingMessageListenerAdapter.CONTEXT_ACKNOWLEDGMENT);
                Object consumer = retryContext.getAttribute(RetryingMessageListenerAdapter.CONTEXT_CONSUMER);
                if (ack instanceof Acknowledgment) {
                    ((Acknowledgment) ack).acknowledge();
                }

                final String topic = record.topic();
                final String groupId = KafkaUtils.getConsumerGroupId();
                String result = postProcessor.getIdByTopic().get(topic + groupId);
                return kafkaException;
            } else {
                log.info("retry exception");
            }
            throw kafkaException;
        });
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000L);
        backOffPolicy.setMaxInterval(1000L);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(4));

        final RetryTemplateBuilder builder = RetryTemplate
                .builder()
                .exponentialBackoff(1000, ExponentialBackOffPolicy.DEFAULT_MULTIPLIER, 1001)
                .maxAttempts(3);
        builder.notRetryOn(IOException.class);
        factory.setRetryTemplate(builder.traversingCauses().build());

        return factory;
    }

    @Bean
    public Receiver receiver() {
        return new Receiver();
    }

}
