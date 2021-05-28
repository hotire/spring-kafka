package com.github.hotire.springkafka.reactive.producer;

import java.util.Map;

import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderResult;

public class ReactiveProducer {

    private final ReactiveKafkaProducerTemplate<String, String> sender;

    public ReactiveProducer(Map<String, Object> props) {
        sender = new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
    }

    public Mono<SenderResult<Void>> send(final String topic, final String key, final String message) {
        return sender.send(topic, key, message);
    }
}
