package com.doubleat.ccgame.controller;

import com.doubleat.ccgame.dto.common.ChatMessage;
import com.doubleat.ccgame.dto.common.MoveMessage;
import com.doubleat.ccgame.dto.common.ReadyMessage;
import com.doubleat.ccgame.dto.response.MessageResponse;
import com.doubleat.ccgame.room.RoomStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class CommunicationController {

    @Autowired
    private RoomStrategy roomStrategy;

    @MessageMapping("/chat/room/{roomId}")
    @SendTo("/chat/room/{roomId}")
    public MessageResponse<?> handleChat(@Payload ChatMessage message,
                                         @DestinationVariable Integer roomId,
                                         Principal principal) {

        message.setUsername(principal.getName());

        return MessageResponse.builder()
                .data(message)
                .type(MessageResponse.MessageResponseType.CHAT)
                .build();
    }

    @MessageMapping("/move/room/{roomId}")
    @SendTo("move/room/{roomId}")
    public MessageResponse<?> handleMove(@Payload MoveMessage move,
                                         @DestinationVariable Integer roomId,
                                         Principal principal) {

        return MessageResponse.builder()
                .data(true)
                .type(MessageResponse.MessageResponseType.MOVE)
                .build();
    }

    @MessageMapping("/ready/room/{roomId}")
    @SendTo("/room/{roomId}")
    public MessageResponse<?> handleReady(@Payload ReadyMessage message,
                                          @DestinationVariable Integer roomId,
                                          Principal principal) {

        roomStrategy.updatePlayerReady(principal.getName(), roomId, message.isReady());

        boolean isGameStarted = roomStrategy.startGame(roomId);

        return MessageResponse.builder()
                .type(MessageResponse.MessageResponseType.READY)
                .data(message)
                .build();
    }

}
