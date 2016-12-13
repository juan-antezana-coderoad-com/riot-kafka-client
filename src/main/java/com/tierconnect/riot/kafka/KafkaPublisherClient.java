package com.tierconnect.riot.kafka;

import com.google.common.base.Preconditions;
import com.tierconnect.riot.RiotMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * KafkaPublisherClient class.
 *
 * @author jantezana
 * @version 2016/12/13
 */
public class KafkaPublisherClient {
    private static Logger logger = Logger.getLogger(KafkaPublisherClient.class);

    private String servers;
    private KafkaProducer<String, String> client;
    private String clientId;

    public KafkaPublisherClient(final String servers,
                                final String clientId) {
        Preconditions.checkNotNull(servers, "The servers is null");
        Preconditions.checkNotNull(clientId, "The clientId is null");
        this.servers = servers;
        this.clientId = clientId;
    }

    /**
     * Initialize the configuration.
     *
     * @throws IOException
     */
    public void init()
    throws IOException {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 0);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("block.on.buffer.full", true);
        props.put("partition.assignment.strategy", "roundrobin");

        client = new KafkaProducer<>(props);
    }

    /**
     * Publish a riot message.
     *
     * @param riotMessage the riot message
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void publish(RiotMessage riotMessage)
    throws ExecutionException, InterruptedException {
        synchronized (this) {
            ProducerRecord<String, String> record = new ProducerRecord<>(riotMessage.getTopic(),
                                                                         riotMessage.getMessage());
            Future<RecordMetadata> result = client.send(record);
            result.get();
        }
    }

    /**
     * Publish a message by partition.
     *
     * @param riotMessage the riot message
     * @param partition   the partition
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void publish(RiotMessage riotMessage,
                        final int partition)
    throws ExecutionException, InterruptedException {
        synchronized (this) {
            ProducerRecord<String, String> record = new ProducerRecord<>(riotMessage.getTopic(),
                                                                         partition, "key",
                                                                         riotMessage.getMessage());
            Future<RecordMetadata> result = client.send(record);
            result.get();
        }
    }

    /**
     * Shutdown.
     */
    public void shutdown() {
        client.close();
    }
}

