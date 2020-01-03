package com.github.hotire.springkafka;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.hotire.springkafka.consumer.Receiver;
import com.github.hotire.springkafka.producer.Sender;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@EmbeddedKafka(partitions = 1,
  topics = {SpringKafkaApplicationTests.HELLOWORLD_TOPIC})
@SpringBootTest
class SpringKafkaApplicationTests {

  static final String HELLOWORLD_TOPIC = "helloworld.t";

  @Autowired
  private Receiver receiver;

  @Autowired
  private Sender sender;

  @Test
  void testReceive() throws Exception {
    final String message = "Hello Spring Kafka!";
    sender.send(message);
    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    receiver.getLatch().await();
    assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    assertThat(receiver.getMessages()).contains(message);
  }

}
