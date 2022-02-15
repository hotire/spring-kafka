package com.github.hotire.springkafka.record_accumulator;

import org.apache.kafka.common.header.Header;

/**
 * @see org.apache.kafka.common.record.MemoryRecordsBuilder
 */
public class CustomMemoryRecordsBuilder {

    /**
     * @see org.apache.kafka.clients.producer.internals.ProducerBatch#tryAppendForSplit
     * @see org.apache.kafka.common.record.MemoryRecordsBuilder#hasRoomFor(long, byte[], byte[], Header[])
     */
    public boolean hasRoomFor(long timestamp, byte[] key, byte[] value, Header[] headers) {
        return true;
    }

}
