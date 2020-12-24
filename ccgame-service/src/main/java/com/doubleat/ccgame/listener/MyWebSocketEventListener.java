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
import java.util.Objects;

@Component
public class MyWebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(MyWebSocketEventListener.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserService userService;

    private static final String REGEX_IN_ROOM = "^/room/[0-9]+$";

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        Principal userPrincipal = event.getUser();
        assert userPrincipal != null;
        logger.info("Received a new web socket connection form userId: {}", userPrincipal.getName());
    }

    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String destination = headerAccessor.getDestination();
        String username = Objects.requireNonNull(event.getUser()).getName();

        logger.info("Username: {} have just subscribe to {}", username, destination);

        // Send noti to user to know have another player join room.
        if (destination != null && destination.matches(REGEX_IN_ROOM)) {
            UserDto userDto = userService.getDtoByUsername(username);
            messagingTemplate.convertAndSend(destination, userDto);
        }
    }

    @EventListener
    public void handleUnSubscribe(SessionUnsubscribeEvent event) {
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        Principal userPrincipal = event.getUser();
        assert userPrincipal != null;
        logger.info("UserId {} had disconnected!", userPrincipal.getName());
    }

}
