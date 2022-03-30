package com.github.hotire.springkafka.interview.client;

import java.util.Map;

import lombok.RequiredArgsConstructor;

/**
 * @see org.apache.kafka.clients.ClusterConnectionStates
 */
@RequiredArgsConstructor
public class ClusterConnectionStatesCore {
    private final Map<String, NodeConnectionState> nodeState;

    private static class NodeConnectionState {

    }
}
