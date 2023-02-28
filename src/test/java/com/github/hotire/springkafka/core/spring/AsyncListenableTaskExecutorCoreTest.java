package com.github.hotire.springkafka.core.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

class AsyncListenableTaskExecutorCoreTest {

    @Test
    void submitListenable() throws ExecutionException, InterruptedException {
        // given
        final AsyncListenableTaskExecutor executor = new SimpleAsyncTaskExecutor();
        final String expected = "hello";

        // when
        final ListenableFuture<String> result = executor.submitListenable(() -> expected);

        // then
        assertThat(result.get()).isEqualTo(expected);
    }

}