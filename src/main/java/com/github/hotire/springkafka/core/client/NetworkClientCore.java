package com.github.hotire.springkafka.core.client;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.NetworkClient;
import org.apache.kafka.common.network.Selectable;

/**
 * @see NetworkClient
 */
@RequiredArgsConstructor
public class NetworkClientCore {

    /**
     * @see NetworkClient#selector
     */
    private final Selectable selector;

}
