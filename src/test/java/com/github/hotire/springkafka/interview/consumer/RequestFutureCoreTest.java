package com.github.hotire.springkafka.interview.consumer;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.kafka.clients.consumer.internals.RequestFuture;
import org.apache.kafka.clients.consumer.internals.RequestFutureAdapter;
import org.junit.jupiter.api.Test;

class RequestFutureCoreTest {

    @Test
    void compose() {
        // given
        final RequestFutureCore<String> core = new RequestFutureCore<>();

        // when
        final RequestFuture<Integer> result = core.compose(new RequestFutureAdapter<String, Integer>() {
                         @Override
                         public void onSuccess(String value, RequestFuture<Integer> future) {
                             future.complete(Integer.valueOf(value));
                         }
                     }
        );

        // then
        assertThat(core.isDone()).isFalse();
        core.complete("1");
        assertThat(core.isDone()).isTrue();
        assertThat(result.isDone()).isTrue();
        assertThat(result.value()).isEqualTo(1);
    }
}