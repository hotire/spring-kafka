package com.github.hotire.springkafka;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import com.github.hotire.springkafka.getting_started.consumer.Receiver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DirtiesContext
@EmbeddedKafka(partitions = 1,
               topics = { WordCountExampleKafkaStreamsTest.INPUT_TOPIC, WordCountExampleKafkaStreamsTest.OUTPUT_TOPIC },
               brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" }
)
@SpringBootTest
class WordCountExampleKafkaStreamsTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private Receiver receiver;

    public static final String INPUT_TOPIC = "wordcount-input";
    public static final String OUTPUT_TOPIC = "wordcount-output";

    @Test
    void stream() throws InterruptedException {
        kafkaTemplate.send(INPUT_TOPIC,"hotire", "Sending with own simple KafkaProducer, Sending");

        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-id");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, String> source = builder.stream(INPUT_TOPIC);

        final Pattern pattern = Pattern.compile("\\W+");
        KStream<String, String> counts = source.flatMapValues(value -> Arrays.asList(pattern.split(value.toLowerCase())))
                                               .map((key, value) -> {
                                                   log.info("key :{}, value : {}",key, value);
                                                   return new KeyValue<>(value, value);
                                               })
                                               .filter((key, value) -> (!value.equals("the")))
                                               .groupByKey()
                                               .count()
                                               .mapValues(value -> Long.toString(value))
                                               .toStream();
        counts.to(OUTPUT_TOPIC);

        try (KafkaStreams streams = new KafkaStreams(builder.build(), props)) {
            // This is for reset to work. Don't use in production - it causes the app to re-load the state from Kafka on every start
            streams.cleanUp();

            streams.start();
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        }
    }

}
