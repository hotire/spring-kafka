package com.github.hotire.springkafka.interview.client;

import java.util.Collections;
import java.util.List;

import org.apache.kafka.clients.ClientResponse;

/**
 * @see org.apache.kafka.clients.KafkaClient
 * @see org.apache.kafka.clients.NetworkClient
 */
public class NetworkClientCore {

    /**
     * @see org.apache.kafka.clients.NetworkClient#poll(long, long) 
     */
    public List<ClientResponse> poll(long timeout, long now) {
        return Collections.emptyList();
    }
}
