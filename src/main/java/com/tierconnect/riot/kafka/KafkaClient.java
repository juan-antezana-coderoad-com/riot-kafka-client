package com.tierconnect.riot.kafka;



import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * KafkaClient class.
 *
 * @author jantezana
 * @version 2016/12/13
 */
public class KafkaClient {
    private final static Logger logger = Logger.getLogger(KafkaClient.class);

    /**
     * Main method.
     *
     * @param args the arguments
     */
    public static void main(String args[]) {
        try {
            KafkaPublisherConfigurationParser parser = new KafkaPublisherConfigurationParser();
            parser.initializeOptions();
            parser.parseOptions(args);

            KafkaPublisherConfiguration configuration = parser.parserConfiguration().get();
            final String servers = configuration.getServers();
            final String clientId = configuration.getClientId();
            final String topic = configuration.getTopic();
            final String fileName = configuration.getFileName();
            final int partition = configuration.getPartition();
            final String message = readFileName(fileName);
            KafkaMessage kafkaMessage = new KafkaMessage(topic, message);

            KafkaPublisherClient client = new KafkaPublisherClient(servers, clientId);
            client.init();
            if (partition != -1) {
                client.publish(kafkaMessage, partition);
                logger.info(String.format("Topic: %s\nMessage: %s\nPartition: %d", kafkaMessage.getTopic(), kafkaMessage.getMessage(), partition));
            } else {
                client.publish(kafkaMessage);
                logger.info(String.format("Topic: %s\nMessage: %s", kafkaMessage.getTopic(), kafkaMessage.getMessage()));
            }
        } catch (KafkaPublisherException e) {
            logger.error(e.getMessage(), e.getCause());
            System.exit(1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            System.exit(1);
        }
    }

    /**
     * Reads a file name.
     *
     * @param fileName the file name
     * @return the body of the file
     */
    private static String readFileName(String fileName) {
        StringBuilder builder = new StringBuilder();
        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> builder.append(line).append('\n'));
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
        }
        return builder.toString();
    }
}
