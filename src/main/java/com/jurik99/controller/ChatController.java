package com.jurik99.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.jurik99.model.Message;
import com.jurik99.model.OutputMessage;

/**
 * Chat controller that listens to chat topic and
 * responds with a {@link OutputMessage}
 */
@Controller
public class ChatController {

    @MessageMapping("/chat/{capturedTemplateVariable}")
    @SendTo("/topic/messages")
    public OutputMessage send(@DestinationVariable("capturedTemplateVariable") final String capturedTemplateVariable,
                              final Message message) {
        return new OutputMessage(message.getFrom(), message.getText(), capturedTemplateVariable);
    }
}
