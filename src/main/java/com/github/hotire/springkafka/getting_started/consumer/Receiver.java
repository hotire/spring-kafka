package com.github.hotire.springkafka.getting_started.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import com.github.hotire.springkafka.getting_started.SkippableException;

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

  @KafkaListener(topics = "helloworld.t", groupId = "test")
  public void receive(ConsumerRecord<String, String> payload, Acknowledgment acknowledgment) {
    log.info("received payload : {}", payload);
    messages.add(payload);
    latch.countDown();
    acknowledgment.acknowledge();
  }

  @KafkaListener(topics = "helloworld.t", groupId = "test2")
  public void receive2(ConsumerRecord<String, String> payload, Acknowledgment acknowledgment) {
    log.info("received2 payload : {}", payload);
    if (1 == 1) {
      throw new SkippableException("hello");
    }
    acknowledgment.acknowledge();
  }
}
