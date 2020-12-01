package com.doubleat.ccgame.controller;

import com.doubleat.ccgame.dto.common.ChatMessage;
import com.doubleat.ccgame.dto.common.MoveMessage;
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

    @MessageMapping("/chat/{roomId}")
    @SendTo("/room/{roomId}")
    public MessageResponse<?> handleChat(@Payload ChatMessage message,
                                         @DestinationVariable Integer roomId,
                                         Principal principal) {

        message.setUsername(principal.getName());

        return MessageResponse.builder()
                .data(message)
                .type(MessageResponse.MessageResponseType.CHAT)
                .build();
    }

    @MessageMapping("/move/{roomId}")
    @SendTo("/room/{roomId}")
    public MessageResponse<?> handleMove(@Payload MoveMessage move,
                                         @DestinationVariable Integer roomId,
                                         Principal principal) {

        return MessageResponse.builder()
                .data(true)
                .type(MessageResponse.MessageResponseType.MOVE)
                .build();
    }

    @MessageMapping("/ready/{roomId}")
    @SendTo("/room/{roomId}")
    public boolean handleReady(@Payload boolean ready,
                               @DestinationVariable Integer roomId,
                               Principal principal) {

        roomStrategy.updatePlayerReady(principal.getName(), roomId, ready);

        return roomStrategy.startGame(roomId);
    }

}
