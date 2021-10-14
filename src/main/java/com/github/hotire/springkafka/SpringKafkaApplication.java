package com.github.hotire.springkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class SpringKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }

}
