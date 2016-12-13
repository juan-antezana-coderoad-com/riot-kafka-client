package com.tierconnect.riot.kafka;

import com.google.common.base.Optional;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * KafkaPublisherConfigurationParser class.
 *
 * @author jantezana
 * @version 2016/12/13
 */
public class KafkaPublisherConfigurationParser {
    private Options options;
    private CommandLine line;

    /**
     * Default constructor of KafkaPublisherConfigurationParser.
     */
    public KafkaPublisherConfigurationParser() {
        this.options = new Options();
    }

    /**
     * Initialize the options.
     */
    public void initializeOptions() {
        options.addOption("s", true, "servers of kafka (default 'localhost:9092')");
        options.addOption("ci", true, " client ID (default 'KafkaClient0001')");
        options.addOption("t", true, "the topic");
        options.addOption("f", true, "file path");
        options.addOption("p", true, "partition number (default '-1')");
        options.addOption("help", false, "show this help");
    }

    /**
     * Parses the options.
     *
     * @param args the arguments
     */
    public void parseOptions(final String[] args) {
        CommandLineParser parser = new BasicParser();

        try {
            line = parser.parse(options, args);
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(String.format("java -cp xxxx KafkaClient"), options);
            System.exit(1);
        }

        if (line.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(String.format("java -cp xxxx KafkaClient"), options);
            System.exit(1);
        }
    }

    /**
     * Parses the configuration.
     *
     * @return the MqttKafkaBridgeConfiguration
     * @throws KafkaPublisherException
     */
    public Optional<KafkaPublisherConfiguration> parserConfiguration()
    throws KafkaPublisherException {
        KafkaPublisherConfiguration configuration;
        try {


            // Rest configuration.

            String servers = line.hasOption("s") ? line.getOptionValue("s") : "localhost:9092";
            String clientId = line.hasOption("ci") ? line.getOptionValue("ci") : "KafkaClient0001";
            String topic = line.getOptionValue("t");
            String fileName = line.getOptionValue("f");
            int partition = line.hasOption("p") ? Integer.parseInt(line.getOptionValue("p")) : -1;

            configuration = new KafkaPublisherConfiguration(servers, clientId, topic, fileName,
                                                            partition);
        } catch (Exception e) {
            throw new KafkaPublisherException(e.getMessage(), e.getCause());
        }

        return Optional.fromNullable(configuration);
    }
}
