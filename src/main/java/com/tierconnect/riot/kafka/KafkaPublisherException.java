package com.tierconnect.riot.kafka;

/**
 * KafkaPublisherException class.
 *
 * @author jantezana
 * @version 2016/12/13
 */
public class KafkaPublisherException extends Exception {

    /**
     * Builds an instance of KafkaPublisherException
     *
     * @param message the message
     */
    public KafkaPublisherException(final String message) {
        super(message);
    }

    /**
     * Builds an instance of KafkaPublisherException
     *
     * @param message the message
     * @param cause   the cause of the error
     */
    public KafkaPublisherException(final String message,
                                   Throwable cause) {
        super(message, cause);
    }
}
