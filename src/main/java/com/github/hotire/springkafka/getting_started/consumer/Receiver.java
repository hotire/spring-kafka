package com.github.hotire.springkafka.getting_started.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Receiver {

  private final CountDownLatch latch;
  private final List<Object> messages;

  public Receiver() {
    this.latch = new CountDownLatch(1);
    this.messages = new ArrayList<>();
  }

  public Receiver(CountDownLatch latch, List<Object> messages) {
    this.latch = latch;
    this.messages = messages;
  }

  @KafkaListener(topics = "helloworld.t")
  public void receive(String payload) {
    log.info("received payload : {}", payload);
    messages.add(payload);
    latch.countDown();
  }
}
