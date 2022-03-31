package com.github.hotire.springkafka.interview.client;

/**
 * @see org.apache.kafka.clients.ConnectionState
 */
public enum ConnectionStateCore {
    DISCONNECTED, CONNECTING, CHECKING_API_VERSIONS, READY, AUTHENTICATION_FAILED;

    public boolean isDisconnected() {
        return this == AUTHENTICATION_FAILED || this == DISCONNECTED;
    }

    public boolean isConnected() {
        return this == CHECKING_API_VERSIONS || this == READY;
    }
}
