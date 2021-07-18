package com.github.hotire.springkafka.getting_started;

import org.springframework.core.NestedRuntimeException;

public class SkippableException extends NestedRuntimeException {
    public SkippableException(String msg) {
        super(msg);
    }

    public SkippableException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
