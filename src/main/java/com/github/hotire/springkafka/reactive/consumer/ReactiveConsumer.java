package com.github.hotire.springkafka.reactive.consumer;

import java.util.Map;

import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;

import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

public class ReactiveConsumer {
    private final ReactiveKafkaConsumerTemplate<String, String> consumer;

    public ReactiveConsumer(Map<String, Object> props) {
        consumer = new ReactiveKafkaConsumerTemplate<>(ReceiverOptions.create(props));
    }

    public Flux<ReceiverRecord<String, String>> consume() {
        return consumer.receive();
    }
}
