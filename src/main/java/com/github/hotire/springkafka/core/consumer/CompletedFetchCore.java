package com.github.hotire.springkafka.core.consumer;

import org.apache.kafka.clients.consumer.internals.Fetcher;
import org.apache.kafka.common.record.Record;
import org.apache.kafka.common.utils.CloseableIterator;

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
     * @see Fetcher.CompletedFetch#records
     */
    private CloseableIterator<Record> records;


    /**
     * @see Fetcher.CompletedFetch#drain()
     */
    public void drain() {

    }

    /**
     * @see Fetcher.CompletedFetch#nextFetchedRecord()
     */
    public Record nextFetchedRecord() {
        return records.next();
    }

}
