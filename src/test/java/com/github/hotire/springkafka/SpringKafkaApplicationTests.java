package com.github.hotire.springkafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import com.github.hotire.springkafka.getting_started.consumer.Receiver;
import com.github.hotire.springkafka.getting_started.producer.Sender;

@DirtiesContext
@EmbeddedKafka(partitions = 1,
               topics = { SpringKafkaApplicationTests.HELLOWORLD_TOPIC },
               brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" }
)
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
//    assertThat(receiver.getMessages()).contains(message);
        Thread.sleep(4000L);
    }

}
