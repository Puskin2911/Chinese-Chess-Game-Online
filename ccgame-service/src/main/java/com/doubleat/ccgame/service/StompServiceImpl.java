package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.message.MoveMessage;
import com.doubleat.ccgame.dto.message.ReadyMessage;
import com.doubleat.ccgame.dto.response.PlayingGameDto;
import com.doubleat.ccgame.dto.response.GameStartResponse;
import com.doubleat.ccgame.dto.response.GameStopResponse;
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
        Optional<PlayingGameDto> gameDtoOptional = roomStrategy.startGame(roomId);

        if (gameDtoOptional.isPresent()) {
            PlayingGameDto playingGameDto = gameDtoOptional.get();
            sendMessage("/room/" + roomId + "/game/start",
                    new GameStartResponse(playingGameDto.getRedPlayerUsername()));
            sendMessage("/room/" + roomId + "/move", playingGameDto);
        } else {
            sendMessage("/room/" + roomId + "/ready", message);
        }
    }

    @Override
    public void handleMove(MoveMessage move, String username, Integer roomId) {
        PlayingGameDto playingGameDto = roomStrategy.handleMove(move, username, roomId);
        sendMessage("/room/" + roomId + "/move", playingGameDto);

        Optional<GameStopResponse> gameStopResponseOptional = roomStrategy.isGameOver(roomId);

        gameStopResponseOptional
                .ifPresent(gameStopResponse -> sendMessage("/room/" + roomId + "/game/start", gameStopResponse));
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