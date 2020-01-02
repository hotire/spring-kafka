package com.github.hotire.springkafka.producer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor
public class Sender {
  private final KafkaTemplate<String, String> kafkaTemplate;

  public void send(final String payload) {
    log.info("sending payload : {}", payload);
    kafkaTemplate.send("helloworld.t", payload);
  }
}
