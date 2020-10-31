package com.doubleat.ccgame.controller;

import com.doubleat.ccgame.dto.common.ChatMessage;
import com.doubleat.ccgame.dto.common.MoveMessage;
import com.doubleat.ccgame.service.RoomService;
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
    private RoomService roomService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/room/{roomId}")
    public ChatMessage handleChat(@Payload ChatMessage message,
                                  @DestinationVariable Integer roomId,
                                  Principal principal) {
        message.setUsername(principal.getName());
        return message;
    }

    @MessageMapping("/move/{roomId}")
    @SendTo("/room/{roomId}")
    public MoveMessage handleMove(@Payload MoveMessage move,
                                  @DestinationVariable Integer roomId,
                                  Principal principal) {
        return move;
    }

    @MessageMapping("/ready/{roomId}")
    public boolean handleReady(@Payload boolean ready,
                               @DestinationVariable Integer roomId,
                               Principal principal) {

        roomService.updatePlayerReady(principal.getName(), roomId, ready);

        return roomService.startGame(roomId);
    }

}
