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
    }
}
