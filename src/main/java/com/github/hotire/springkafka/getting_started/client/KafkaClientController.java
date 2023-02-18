package com.github.hotire.springkafka.getting_started.client;

import java.lang.reflect.Field;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/kafka")
public class KafkaClientController {
    private final ConsumerFactory consumerFactory;
    private final ConsumerNetworkClient client;

    public KafkaClientController(ConsumerFactory consumerFactory) {
        this.consumerFactory = consumerFactory;
        try (final Consumer<String, String> consumer = consumerFactory.createConsumer()) {
            final KafkaConsumer<String, String> kafkaConsumer = KafkaConsumer.class.cast(consumer);
            final Field field = KafkaConsumer.class.getDeclaredField("client");
            field.setAccessible(true);
            final ConsumerNetworkClient client = (ConsumerNetworkClient) field.get(kafkaConsumer);
            this.client = client;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
