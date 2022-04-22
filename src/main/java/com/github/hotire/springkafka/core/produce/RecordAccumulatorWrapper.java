package com.github.hotire.springkafka.core.produce;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.internals.FutureRecordMetadata;
import org.apache.kafka.clients.producer.internals.ProducerBatch;
import org.apache.kafka.clients.producer.internals.RecordAccumulator;
import org.apache.kafka.clients.producer.internals.RecordAccumulator.RecordAppendResult;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.utils.Time;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecordAccumulatorWrapper {
    private final RecordAccumulator recordAccumulator;
    private final Time time;

    public RecordAppendResult append(TopicPartition tp,
                                     long timestamp,
                                     byte[] key,
                                     byte[] value,
                                     Header[] headers,
                                     Callback callback,
                                     long maxTimeToBlock,
                                     boolean abortOnNewBatch,
                                     long nowMs) throws InterruptedException {
        return recordAccumulator.append(tp, timestamp, key, value, headers, callback, maxTimeToBlock, abortOnNewBatch, nowMs);
    }

    public Map<Integer, List<ProducerBatch>> drain(Cluster cluster, Set<Node> nodes, int maxSize, long now) {
        return recordAccumulator.drain(cluster, nodes, maxSize, now);
    }

    /**
     *
     * @see org.apache.kafka.clients.producer.internals.RecordAccumulator#append(TopicPartition, long, byte[], byte[], Header[], Callback, long, boolean, long)
     * @see org.apache.kafka.clients.producer.internals.RecordAccumulator#tryAppend(long, byte[], byte[], Header[], Callback, Deque, long)
     *
     * Deque의 Last에서 RecordBatch 하나를 꺼내서 Record를 저장할 공간이 있는지 확인한다.
     * 여유 공간이 있으면 해당 RecordBatch에 Record를 추가하고, 여유 공간이 없으면 새로운 RecordBatch를 생성해서 Last쪽으로 저장한다.
     * Queue를 사용하지 않고 Deque가 사용된 이유는 append() 시에 가장 최근에 들어간 RecordBatch를 꺼내서 봐야 하기 때문이다.
     *   if (future == null) last.closeForRecordAppends(); 을 하고 null을 리턴한다.
     */
    public RecordAppendResult tryAppend(long timestamp, byte[] key, byte[] value, Header[] headers,
                                        Callback callback, Deque<ProducerBatch> deque, long nowMs) {
        ProducerBatch last = deque.peekLast();
        if (last != null) {
            FutureRecordMetadata future = last.tryAppend(timestamp, key, value, headers, callback, time.milliseconds());
            if (future == null) {
                last.closeForRecordAppends();
            } else {
                return new RecordAppendResult(future, deque.size() > 1 || last.isFull(), false, false);
            }
        }
        return null;
    }
}
