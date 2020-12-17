package com.doubleat.ccgame.listener;

import com.doubleat.ccgame.dto.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    // Cache to query when user unSubscribe
    private final Map<String, String> cachedDestinationPerSubscribeMap = new HashMap<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        Principal userPrincipal = event.getUser();
        assert userPrincipal != null;
        logger.info("Received a new web socket connection form userId: {}", userPrincipal.getName());
    }

    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String subscriptionId = getUniqueSubscriptionId(headerAccessor);
        String destination = headerAccessor.getDestination();

        cachedDestinationPerSubscribeMap.put(subscriptionId, destination);

        buildAndSendMessage(MessageResponse.MessageResponseType.JOIN_ROOM, destination);
    }

    @EventListener
    public void handleUnSubscribe(SessionUnsubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String subscriptionId = getUniqueSubscriptionId(headerAccessor);

        buildAndSendMessage(MessageResponse.MessageResponseType.LEAVE_ROOM,
                cachedDestinationPerSubscribeMap.get(subscriptionId));

        // Remove subscriptionId will unSubscription
        cachedDestinationPerSubscribeMap.remove(subscriptionId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        Principal userPrincipal = event.getUser();
        assert userPrincipal != null;
        logger.info("UserId {} had disconnected!", userPrincipal.getName());
    }

    /**
     * Get unique subscriptionId from sessionId and subscriptionId.
     *
     * @param headerAccessor {@code StompHeaderAccessor} object to access stomp header.
     * @return Unique subscriptionId.
     */
    private String getUniqueSubscriptionId(StompHeaderAccessor headerAccessor) {
        return headerAccessor.getDestination() + "+" + headerAccessor.getSubscriptionId();
    }

    private void buildAndSendMessage(MessageResponse.MessageResponseType messageResponseType,
                                     String destination) {
        MessageResponse<?> messageResponse = MessageResponse.builder()
                .type(messageResponseType)
                .build();

        try {
            messagingTemplate.convertAndSend(Objects.requireNonNull(destination), messageResponse);
        } catch (NullPointerException e) {
            logger.error("destinationId must be not null!");
        } catch (MessagingException e) {
            logger.error("Can't send message to {}", destination);
        }
    }

}
