package com.github.hotire.springkafka.core.admin;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListConsumerGroupOffsetsResult;


/**
 * @see Admin
 * @see AdminClient
 * @see KafkaAdminClient
 */
public interface KafkaAdminClientCore {

    /**
     * @see Admin#listConsumerGroupOffsets(String) 
     */
    ListConsumerGroupOffsetsResult listConsumerGroupOffsets(String groupId);
}
