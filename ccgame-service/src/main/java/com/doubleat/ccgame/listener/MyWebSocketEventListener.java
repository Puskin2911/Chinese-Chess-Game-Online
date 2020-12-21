package com.doubleat.ccgame.listener;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.room.RoomStrategy;
import com.doubleat.ccgame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
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
public class MyWebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketEventListener.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomStrategy roomStrategy;

    // Cache to query when user unSubscribe
    private final Map<String, String> cachedDestinationPerSubscribeMap = new HashMap<>();

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
        String username = Objects.requireNonNull(event.getUser()).getName();

        logger.info("Username: {} have just subscribe to {}", username, destination);

        UserDto userDto = userService.getDtoByUsername(username);

        cachedDestinationPerSubscribeMap.put(subscriptionId, destination);

    }

    @EventListener
    public void handleUnSubscribe(SessionUnsubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String subscriptionId = getUniqueSubscriptionId(headerAccessor);
        String username = Objects.requireNonNull(event.getUser()).getName();

        UserDto userDto = userService.getDtoByUsername(username);

        // Remove subscriptionId will unSubscription
        cachedDestinationPerSubscribeMap.remove(subscriptionId);

        roomStrategy.kickOutPlayer(userDto);
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

}
