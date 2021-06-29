package com.github.hotire.springkafka.core.produce;

import org.apache.kafka.common.protocol.ApiKeys;
import org.apache.kafka.common.protocol.types.Struct;
import org.apache.kafka.common.requests.AbstractRequest;
import org.apache.kafka.common.requests.AbstractResponse;
import org.apache.kafka.common.requests.ProduceRequest;

import lombok.Getter;

public class ProduceRequestDecorator extends AbstractRequest {

    @Getter
    private final ProduceRequest delegate;

    public ProduceRequestDecorator(ProduceRequest delegate, ApiKeys api, short version) {
        super(api, version);
        this.delegate = delegate;
    }

    @Override
    protected Struct toStruct() {
        return getDelegate().toStruct();
    }

    @Override
    public AbstractResponse getErrorResponse(int throttleTimeMs, Throwable e) {
        return getDelegate().getErrorResponse(throttleTimeMs, e);
    }
}
