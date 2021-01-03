package com.doubleat.ccgame.controller;

import com.doubleat.ccgame.dto.message.*;
import com.doubleat.ccgame.service.StompService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class StompController {

    private static final Logger logger = LoggerFactory.getLogger(StompController.class);

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
    public void handleMove(@Payload MoveMessage move,
                           @DestinationVariable Integer roomId,
                           Principal principal) {
        logger.info("Receive a MoveMessage from client!, {}", move.toString());

        stompService.handleMove(move, principal.getName(), roomId);
    }

    @MessageMapping("/room/{roomId}/ready")
    public void handleReady(@Payload ReadyMessage message,
                            @DestinationVariable Integer roomId,
                            Principal principal) {
        logger.info("Receive a ReadyMessage from client!, {}", message.toString());

        stompService.handlePlayerReady(message, roomId, principal.getName());
    }

    @MessageMapping("/room/{roomId}/game/draw-request")
    @SendTo("/room/{roomId}/game/draw-request")
    public DrawRequest handleDrawRequest(Principal principal) {
        DrawRequest drawRequest = new DrawRequest();
        drawRequest.setUsername(principal.getName());

        return drawRequest;
    }

    @MessageMapping("/room/{roomId}/game/draw-response")
    public void handleDrawResponse(@DestinationVariable Integer roomId,
                                   @Payload boolean isAgree,
                                   Principal principal) {
        stompService.handleDrawResponse(roomId, isAgree, principal.getName());
    }

    @MessageMapping("/room/{roomId}/game/surrender-request")
    public void handleSurrenderRequest(@DestinationVariable Integer roomId,
                                       Principal principal) {
        stompService.handleSurrenderRequest(roomId, principal.getName());
    }

    @MessageMapping("/room/{roomId}/game/time-over")
    public void handleTimeOver(@DestinationVariable Integer roomId,
                               @Payload TimeOverMessage timeOverMessage) {
        stompService.handleTimeOver(roomId, timeOverMessage.getUsername());
    }

}
