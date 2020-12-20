package com.doubleat.ccgame.controller;

import com.doubleat.ccgame.dto.common.ChatMessage;
import com.doubleat.ccgame.dto.common.MoveMessage;
import com.doubleat.ccgame.dto.common.ReadyMessage;
import com.doubleat.ccgame.dto.response.MessageResponse;
import com.doubleat.ccgame.room.RoomStrategy;
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
    public ReadyMessage handleReady(@Payload ReadyMessage message,
                                    @DestinationVariable Integer roomId,
                                    Principal principal) {
        logger.info("Receive a ReadyMessage from client!, {}", message.toString());

        boolean isGameStarted = roomStrategy.updatePlayerReady(principal.getName(), roomId, message.isReady());

        if (isGameStarted) {
            try {
                messagingTemplate.convertAndSend("/room/" + roomId + "/game/start", "START");
                logger.info("Message was sent to start game!");
            } catch (MessagingException e) {
                logger.error("Can not send start message to user");
            }
        }

        logger.info("ReadyMessage before send to user, {}", message.toString());

        return message;
    }

}
