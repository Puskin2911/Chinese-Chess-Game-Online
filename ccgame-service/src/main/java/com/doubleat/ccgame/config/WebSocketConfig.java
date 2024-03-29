package com.doubleat.ccgame.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private StompConfig stompConfig;

    @Autowired
    private MvcConfig mvcConfig;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp")
                .setAllowedOrigins(mvcConfig.getAllowedOrigins().toArray(new String[0]))
                .withSockJS()
                .setStreamBytesLimit(stompConfig.getStreamBytesLimit())
                .setHttpMessageCacheSize(stompConfig.getHttpMessageCacheSize())
                .setDisconnectDelay(stompConfig.getDisconnectDelay());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/room");
        registry.setApplicationDestinationPrefixes("/app");
    }

}
