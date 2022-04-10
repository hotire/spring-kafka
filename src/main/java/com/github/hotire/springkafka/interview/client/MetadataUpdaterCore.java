package com.github.hotire.springkafka.interview.client;

import org.apache.kafka.common.Node;
import org.apache.kafka.common.requests.MetadataRequest;
import org.apache.kafka.common.requests.MetadataRequest.Builder;

/**
 * @see org.apache.kafka.clients.MetadataUpdater
 * @see org.apache.kafka.clients.NetworkClient.DefaultMetadataUpdater
 *
 * @see org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG
 * @see org.apache.kafka.clients.CommonClientConfigs.METADATA_MAX_AGE_CONFIG // 마지막 메타데이터 갱신이 성공한 이후 metadata.max.age.ms에 설정한 시간이 지나면 다시 갱신 요청을 전송한다
 */
public class MetadataUpdaterCore {

    private boolean needUpdate;

    private long lastSuccessfulRefreshMs;
    private long metadataExpireMs;

    public synchronized long timeToNextUpdate(long nowMs) {
        long timeToExpire = needUpdate ? 0 : Math.max(this.lastSuccessfulRefreshMs + this.metadataExpireMs - nowMs, 0);
        return 0L;
    }

    /**
     * @see org.apache.kafka.clients.NetworkClient.DefaultMetadataUpdater#maybeUpdate(long, Node)
     */
    public long maybeUpdate(long now) {
        return 0L;
    }

    /**
     * @see org.apache.kafka.clients.NetworkClient.DefaultMetadataUpdater#sendInternalMetadataRequest(Builder, String, long)
     */
    void sendInternalMetadataRequest(MetadataRequest.Builder builder, String nodeConnectionId, long now) {

    }
}
