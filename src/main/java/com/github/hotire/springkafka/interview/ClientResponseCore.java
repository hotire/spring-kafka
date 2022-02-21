package com.github.hotire.springkafka.interview;

import java.util.List;

import org.apache.kafka.clients.ClientResponse;

/**
 * @see org.apache.kafka.clients.ClientResponse
 */
public class ClientResponseCore {

    /**
     * @see org.apache.kafka.clients.NetworkClient#completeResponses(List)
     * @see ClientResponse#onComplete()
     */
    public void onComplete() {
    }

}
