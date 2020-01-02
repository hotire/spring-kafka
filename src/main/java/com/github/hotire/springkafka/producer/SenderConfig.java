package com.github.hotire.springkafka.producer;


import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class SenderConfig {

  private final String bootstrapServers;

  public SenderConfig(@Value("${kafka.bootstrap-servers}") String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
  }

  @Bean
  public Map<String, Object> producerConfigs() {
    return Map.of(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
    );
  }
  @Bean
  public ProducerFactory<String, String> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean
  public Sender sender() {
    return new Sender(kafkaTemplate());
  }
}
