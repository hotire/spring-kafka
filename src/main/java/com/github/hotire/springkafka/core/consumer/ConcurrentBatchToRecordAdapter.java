package com.github.hotire.springkafka.core.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.BatchToRecordAdapter;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcurrentBatchToRecordAdapter<K, V> implements BatchToRecordAdapter<K, V> {

    final ExecutorService service = Executors.newFixedThreadPool(1);

    @Override
    public void adapt(List<Message<?>> messages, List<ConsumerRecord<K, V>> records, Acknowledgment ack, Consumer<?, ?> consumer, Callback<K, V> callback) {
        final List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            Message<?> message = messages.get(i);
            final int index = i;
            try {
                Future<?> result = service.submit(() -> callback.invoke(records.get(index), ack, consumer, message));
                futures.add(result);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            if (futures.size() > 2) {
                futures.forEach(it -> {
                    try {
                        it.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                });
                futures.clear();
            }
        }
    }
}
