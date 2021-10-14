package com.github.hotire.springkafka.getting_started.producer;

import org.springframework.kafka.core.KafkaTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Sender {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(final String payload) {
        log.info("sending payload : {}", payload);
        kafkaTemplate.send("helloworld.t", payload);
    }
}
