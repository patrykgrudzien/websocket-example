package com.jurik99.model;

import lombok.Data;

/**
 * Message received from the client.
 */
@Data
public class Message {

    private final String from;
    private final String text;
}
