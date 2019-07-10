package com.jurik99.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * This method sets up a simple (in-memory) message broker for our application.
     * The "/topic" designates that any destination prefixed with "/topic" will be routed back to the client.
     *
     * We've also configured 'application destination prefixes' of just "/app".
     * This configuration allows Spring to understand that any message sent to a Websocket
     * channel name prefixed with "/app" should be routed to a "@MessageMapping" in our application.
     *
     * It's important to keep in mind that using "simpleMessageBroker" will not work with more
     * than 1 application instance and it doesn't support all of the features a full message broker like
     * RabbitMQ, ActiveMQ, etc... provide.
     */
    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Here we've also registered some STOMP (Simple Text Oriented Message Protocol) endpoints.
     * STOMP is simply a nice abstraction on top of Websocket to allow us to send text (think JSON)
     * as our message payload. Without STOMP, we would need to rely on some other higher level message protocol,
     * or use the Websocket TCP transport layer raw which would be much less user-friendly for our server and our client.
     * The endpoint "/websocket" will allow us to connect to "ws://localhost:8080/websocket" with the default Spring
     * port configuration. Interestingly we also have "/sockjs" endpoint. This endpoint is special as it uses the
     * SockJS fallback protocol which allows a client that doesn't support WebSocket natively mimic a WebSocket over a HTTP
     * connection. So for the SockJS endpoint, our connector string would look like "http://localhost:8080/sockjs".
     * This is just here as an exercise to show it's possible to configure a fallback if you need to support very old
     * browsers or a client that doesn't support WebSocket natively, but we won't use it for the remainder of the post.
     */
    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket");
        registry.addEndpoint("/sockjs").withSockJS();
    }
}
