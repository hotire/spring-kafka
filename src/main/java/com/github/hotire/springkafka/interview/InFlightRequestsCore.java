package com.github.hotire.springkafka.interview;

import org.apache.kafka.clients.ClientRequest;
import org.apache.kafka.common.requests.AbstractRequest;

/**
 * @see org.apache.kafka.clients.InFlightRequests
 */
public class InFlightRequestsCore {

    /**
     * @see org.apache.kafka.clients.NetworkClient#doSend(ClientRequest, boolean, long, AbstractRequest)
     * @see org.apache.kafka.clients.InFlightRequests#add(InFlightRequest)
     */
    public void add(Object request) {
    }
}
