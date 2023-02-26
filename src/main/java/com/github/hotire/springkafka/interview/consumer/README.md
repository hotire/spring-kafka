# Consumer

## KafkaMessageListenerContainer

### doStart


## KafkaMessageListenerContainer.ListenerConsumer

### run

### pollAndInvoke

### doPoll

## KafkaConsumer 

### poll

~~~java
final Map<TopicPartition, List<ConsumerRecord<K, V>>> records = pollForFetches(timer);
                if (!records.isEmpty()) {
                    // before returning the fetched records, we can send off the next round of fetches
                    // and avoid block waiting for their responses to enable pipelining while the user
                    // is handling the fetched records.
                    //
                    // NOTE: since the consumed position has already been updated, we must not allow
                    // wakeups or any other errors to be triggered prior to returning the fetched records.
                    if (fetcher.sendFetches() > 0 || client.hasPendingRequests()) {
                        client.transmitSends();
                    }

                    return this.interceptors.onConsume(new ConsumerRecords<>(records));
                }
~~~