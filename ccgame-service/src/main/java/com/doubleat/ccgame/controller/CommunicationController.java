package com.doubleat.ccgame.controller;

import com.doubleat.ccgame.dto.common.ChatMessage;
import com.doubleat.ccgame.dto.common.MoveMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class CommunicationController {

    @MessageMapping("/chat/{id}")
    @SendTo("/room/{id}")
    public ChatMessage handleChat(@Payload ChatMessage message,
                                  @DestinationVariable(value = "id") Integer roomId,
                                  Principal principal) {
        message.setUsername(principal.getName());
        return message;
    }

    @MessageMapping("/move/{id}")
    @SendTo("/room/{id}")
    public MoveMessage handleMove(@Payload MoveMessage move,
                                  @DestinationVariable Integer id,
                                  Principal principal) {
        return move;
    }

    @MessageMapping("/start/{roomId}")
    public boolean handleReady(@DestinationVariable Integer roomId,
                                   Principal principal){
        return false;
    }

}
