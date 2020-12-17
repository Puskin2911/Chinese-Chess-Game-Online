package com.doubleat.ccgame.listener;

import com.doubleat.ccgame.dto.common.ChatMessage;
import com.doubleat.ccgame.dto.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        Principal userPrincipal = event.getUser();
        assert userPrincipal != null;
        logger.info("Received a new web socket connection form userId:  {}", userPrincipal.getName());

        MessageResponse<?> messageResponse = MessageResponse.builder()
                .type(MessageResponse.MessageResponseType.JOIN_ROOM)
                .build();

        messagingTemplate.convertAndSend(messageResponse);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        Principal userPrincipal = event.getUser();
        assert userPrincipal != null;
        logger.info("UserId {} had disconnected!", userPrincipal.getName());

        MessageResponse<?> messageResponse = MessageResponse.builder()
                .type(MessageResponse.MessageResponseType.LEAVE_ROOM)
                .build();

        messagingTemplate.convertAndSend(messageResponse);
    }

}
