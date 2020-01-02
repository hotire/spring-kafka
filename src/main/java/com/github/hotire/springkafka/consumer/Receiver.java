package com.github.hotire.springkafka.consumer;


import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class Receiver {

  private final CountDownLatch latch;

  public Receiver() {
    this.latch = new CountDownLatch(1);
  }

  public Receiver(CountDownLatch latch) {
    this.latch = latch;
  }

  public CountDownLatch getLatch() {
    return latch;
  }

  @KafkaListener(topics = "helloworld.t")
  public void receive(String payload) {
    log.info("received payload : {}", payload);
    latch.countDown();
  }
}
