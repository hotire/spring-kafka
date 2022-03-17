package com.github.hotire.springkafka.interview.consumer;

import org.apache.kafka.clients.ClientResponse;
import org.apache.kafka.clients.RequestCompletionHandler;

/**
 * @see org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient.RequestFutureCompletionHandler
 */
class RequestFutureCompletionHandlerCore implements RequestCompletionHandler {

    /**
     * @see org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient.RequestFutureCompletionHandler#onComplete(ClientResponse) 
     */
    @Override
    public void onComplete(ClientResponse response) {
        
    }
}
