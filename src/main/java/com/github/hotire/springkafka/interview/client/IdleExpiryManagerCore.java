package com.github.hotire.springkafka.interview.client;

/**
 * @see org.apache.kafka.clients.CommonClientConfigs.CONNECTIONS_MAX_IDLE_MS_CONFIG
 * @see org.apache.kafka.common.network.Selector.IdleExpiryManager
 *  this.idleExpiryManager = connectionMaxIdleMs < 0 ? null : new IdleExpiryManager(time, connectionMaxIdleMs);
 */
public class IdleExpiryManagerCore {
}
