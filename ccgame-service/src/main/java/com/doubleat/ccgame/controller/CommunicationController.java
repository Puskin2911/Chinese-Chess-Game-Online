package com.doubleat.ccgame.controller;

import com.doubleat.ccgame.dto.message.ChatMessage;
import com.doubleat.ccgame.dto.message.MoveMessage;
import com.doubleat.ccgame.dto.message.ReadyMessage;
import com.doubleat.ccgame.dto.response.GameDto;
import com.doubleat.ccgame.room.RoomStrategy;
import com.doubleat.ccgame.service.StompService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class CommunicationController {

    private static final Logger logger = LoggerFactory.getLogger(CommunicationController.class);

    @Autowired
    private RoomStrategy roomStrategy;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private StompService stompService;

    @MessageMapping("/room/{roomId}/chat")
    @SendTo("/room/{roomId}/chat")
    public ChatMessage handleChat(@Payload ChatMessage message,
                                  @DestinationVariable Integer roomId,
                                  Principal principal) {

        message.setUsername(principal.getName());

        return message;
    }

    @MessageMapping("/room/{roomId}/move")
    @SendTo("/room/{roomId}/move")
    public boolean handleMove(@Payload MoveMessage move,
                              @DestinationVariable Integer roomId,
                              Principal principal) {

        // TODO : handle move here

        return true;
    }

    @MessageMapping("/room/{roomId}/ready")
    @SendTo("/room/{roomId}/ready")
    public void handleReady(@Payload ReadyMessage message,
                            @DestinationVariable Integer roomId,
                            Principal principal) {
        logger.info("Receive a ReadyMessage from client!, {}", message.toString());

        stompService.handlePlayerReady(message, roomId, principal.getName());
    }

}
