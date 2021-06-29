package com.github.hotire.springkafka.core.produce;

import org.apache.kafka.clients.producer.internals.Sender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SenderDecorator implements Runnable {
    @Getter
    private final Sender delegate;

    @Override
    public void run() {
        getDelegate().run();
        // 1. run
        // 2. runOnce
        // 3. sendProducerData
        // 4. accumulator.drain
    }
}
