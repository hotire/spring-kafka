package com.github.hotire.springkafka.core.consumer;

import org.apache.kafka.clients.consumer.internals.Fetcher;

/**
 * @see Fetcher
 * @see Fetcher.CompletedFetch
 */
public class CompletedFetchCore {

    /**
     * @see Fetcher.CompletedFetch#isConsumed
     */
    private boolean isConsumed = false;

    /**
     * @see Fetcher.CompletedFetch#initialized
     */
    private boolean initialized = false;


    /**
     * @see Fetcher.CompletedFetch#drain()
     */
    public void drain() {

    }

}
