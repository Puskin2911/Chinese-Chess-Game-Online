package com.doubleat.ccgame.controller;

import com.doubleat.ccgame.dto.common.ChatMessage;
import com.doubleat.ccgame.dto.common.MoveMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
public class CommunicationController {

    @MessageMapping("/chat/room/{id}")
    @SendTo("/room/{id}")
    public ChatMessage handleChat(@Payload ChatMessage message, @DestinationVariable(value = "id") Integer roomId, Authentication authentication) {
        message.setUsername(authentication.getName());
        return message;
    }

    @MessageMapping("/move/room/{id}")
    @SendTo("/room/{id}")
    public MoveMessage handleMove(@Payload MoveMessage move, @DestinationVariable Integer id) {
        return move;
    }
}
