package com.tierconnect.riot.kafka;

/**
 * KafkaPublisherConfiguration class.
 *
 * @author jantezana
 * @version 2016/12/13
 */
public class KafkaPublisherConfiguration {
    private String servers;
    private String clientId;
    private String topic;
    private String fileName;
    private int partition;

    public KafkaPublisherConfiguration(String servers,
                                       String clientId,
                                       String topic,
                                       String fileName,
                                       int partition) {
        this.servers = servers;
        this.clientId = clientId;
        this.topic = topic;
        this.fileName = fileName;
        this.partition = partition;
    }

    public String getServers() {
        return servers;
    }

    public String getClientId() {
        return clientId;
    }

    public String getTopic() {
        return topic;
    }

    public String getFileName() {
        return fileName;
    }

    public int getPartition() {
        return partition;
    }
}
