package com.github.hotire.springkafka.core.client;

import java.io.IOException;
import java.util.List;

import org.apache.kafka.clients.ClientRequest;
import org.apache.kafka.clients.ClientResponse;
import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.RequestCompletionHandler;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.errors.AuthenticationException;
import org.apache.kafka.common.requests.AbstractRequest.Builder;

public class CustomKafkaClient implements KafkaClient {
    @Override
    public boolean isReady(Node node, long now) {
        return false;
    }

    @Override
    public boolean ready(Node node, long now) {
        return false;
    }

    @Override
    public long connectionDelay(Node node, long now) {
        return 0;
    }

    @Override
    public long pollDelayMs(Node node, long now) {
        return 0;
    }

    @Override
    public boolean connectionFailed(Node node) {
        return false;
    }

    @Override
    public AuthenticationException authenticationException(Node node) {
        return null;
    }

    @Override
    public void send(ClientRequest request, long now) {

    }

    @Override
    public List<ClientResponse> poll(long timeout, long now) {
        return null;
    }

    @Override
    public void disconnect(String nodeId) {

    }

    @Override
    public void close(String nodeId) {

    }

    @Override
    public Node leastLoadedNode(long now) {
        return null;
    }

    @Override
    public int inFlightRequestCount() {
        return 0;
    }

    @Override
    public boolean hasInFlightRequests() {
        return false;
    }

    @Override
    public int inFlightRequestCount(String nodeId) {
        return 0;
    }

    @Override
    public boolean hasInFlightRequests(String nodeId) {
        return false;
    }

    @Override
    public boolean hasReadyNodes(long now) {
        return false;
    }

    @Override
    public void wakeup() {

    }

    @Override
    public ClientRequest newClientRequest(String nodeId, Builder<?> requestBuilder, long createdTimeMs, boolean expectResponse) {
        return null;
    }

    @Override
    public ClientRequest newClientRequest(String nodeId, Builder<?> requestBuilder, long createdTimeMs, boolean expectResponse, int requestTimeoutMs, RequestCompletionHandler callback) {
        return null;
    }

    @Override
    public void initiateClose() {

    }

    @Override
    public boolean active() {
        return false;
    }

    @Override
    public void close() throws IOException {

    }
}
