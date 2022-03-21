package com.github.hotire.springkafka.interview.consumer;

/**
 * @see org.apache.kafka.clients.consumer.internals.SubscriptionState
 */
public class SubscriptionStateCore {
    private enum SubscriptionType {
        NONE, AUTO_TOPICS, AUTO_PATTERN, USER_ASSIGNED
    }
}
