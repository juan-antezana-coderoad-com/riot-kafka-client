package com.tierconnect.riot.kafka;

import com.tierconnect.riot.RiotMessage;

/**
 * KafkaMessage class.
 *
 * @author jantezana
 * @version 2016/12/13
 */
public class KafkaMessage implements RiotMessage {
    private String topic;
    private String message;

    public KafkaMessage(final String topic,
                        final String message) {
        this.topic = topic;
        this.message = message;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
