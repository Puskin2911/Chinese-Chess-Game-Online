package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.message.MoveMessage;
import com.doubleat.ccgame.dto.message.ReadyMessage;
import com.doubleat.ccgame.dto.response.GameDto;
import com.doubleat.ccgame.room.Room;
import com.doubleat.ccgame.room.RoomStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StompServiceImpl implements StompService {

    private static final Logger logger = LoggerFactory.getLogger(StompServiceImpl.class);

    @Autowired
    private RoomStrategy roomStrategy;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void handlePlayerReady(ReadyMessage message, Integer roomId, String name) {
        roomStrategy.updatePlayerReady(name, roomId, message.isReady());
        Optional<GameDto> gameDtoOptional = roomStrategy.startGame(roomId);

        if (gameDtoOptional.isPresent()) {
            sendMessage("/room/" + roomId + "/game/start", "STARTING.......");
            sendMessage("/room/" + roomId + "/move", gameDtoOptional.get());
        } else {
            sendMessage("/room/" + roomId + "/ready", message);
        }
    }

    @Override
    public void handleMove(MoveMessage move, String username, Integer roomId) {
        GameDto gameDto = roomStrategy.handleMove(move, username, roomId);

        sendMessage("/room/" + roomId + "/move", gameDto);
    }

    private void sendMessage(String destination, Object payload) {
        try {
            messagingTemplate.convertAndSend(destination, payload);
            logger.info("Message was sent to {}", destination);
        } catch (MessagingException e) {
            logger.error("Can not send start message to {}", destination);
        }
    }

}
