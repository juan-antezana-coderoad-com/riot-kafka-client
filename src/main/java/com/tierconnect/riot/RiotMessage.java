package com.tierconnect.riot;

/**
 * RiotMessage class.
 *
 * @author jantezana
 * @version 2016/12/13
 */
public interface RiotMessage {
    /**
     * Gets the topic.
     *
     * @return the topic
     */
    String getTopic();

    /**
     * Gets the message
     *
     * @return the message
     */
    String getMessage();
}
