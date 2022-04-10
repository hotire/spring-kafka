package com.github.hotire.springkafka.interview.client;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @see org.apache.kafka.clients.Metadata
 */
public class MetadataCore {

    /**
     * @see org.apache.kafka.clients.Metadata#bootstrap(List, long)
     */
    public synchronized void bootstrap(List<InetSocketAddress> addresses, long now) {

    }
}
