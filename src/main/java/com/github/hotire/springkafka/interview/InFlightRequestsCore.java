package com.github.hotire.springkafka.interview;

import org.apache.kafka.clients.ClientRequest;
import org.apache.kafka.common.requests.AbstractRequest;

/**
 * @see org.apache.kafka.clients.InFlightRequests
 *
 * 전송되었거나 전송 중이지만 아직 응답을 받지 못한 요청 
 */
public class InFlightRequestsCore {

    /**
     * @see org.apache.kafka.clients.NetworkClient#doSend(ClientRequest, boolean, long, AbstractRequest)
     * @see org.apache.kafka.clients.InFlightRequests#add(InFlightRequest)
     */
    public void add(Object request) {
    }
}
