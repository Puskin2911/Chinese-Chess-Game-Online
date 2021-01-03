package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.message.DrawResponse;
import com.doubleat.ccgame.dto.message.MoveMessage;
import com.doubleat.ccgame.dto.message.ReadyMessage;
import com.doubleat.ccgame.dto.response.GameStartResponse;
import com.doubleat.ccgame.dto.response.GameStopResponse;
import com.doubleat.ccgame.dto.response.PlayingGameDto;
import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.game.GameOverReason;
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
        playingGameDto.setMoved(move.getMoveString());
        sendMessage("/room/" + roomId + "/move", playingGameDto);

        Optional<GameStopResponse> gameStopResponseOptional = roomStrategy.handleGameOver(roomId);

        gameStopResponseOptional
                .ifPresent(gameStopResponse -> sendMessage("/room/" + roomId + "/game/stop", gameStopResponse));
    }

    @Override
    public void notifyPlayerLeaveRoom(int roomId, UserDto userDto) {
        sendMessage("/room/" + roomId + "/leave", userDto);
    }

    @Override
    public void notifyPlayerJoinRoom(int roomId, UserDto userDto) {
        sendMessage("/room/" + roomId, userDto);
    }

    @Override
    public void notifyStopGame(int roomId, GameStopResponse gameStopResponse) {
        sendMessage("/room/" + roomId + "/game/stop", gameStopResponse);
    }

    @Override
    public void handleSurrenderRequest(Integer roomId, String loserUsername) {
        GameStopResponse gameStopResponse = roomStrategy.handleSurrenderRequest(roomId, loserUsername);
        gameStopResponse.setReason(GameOverReason.SURRENDER);

        sendMessage("/room/" + roomId + "/game/stop", gameStopResponse);
    }

    @Override
    public void handleTimeOver(Integer roomId, String loserUsername) {
        GameStopResponse gameStopResponse = roomStrategy.handleSurrenderRequest(roomId, loserUsername);
        gameStopResponse.setReason(GameOverReason.TIME_UP);

        sendMessage("/room/" + roomId + "/game/stop", gameStopResponse);
    }

    @Override
    public void handleDrawResponse(Integer roomId, boolean isAgree, String name) {
        DrawResponse drawResponse = DrawResponse.builder()
                .username(name)
                .isAgree(isAgree)
                .build();
        sendMessage("/room/" + roomId + "/game/draw-response", drawResponse);

        if (isAgree) {
            GameStopResponse gameStopResponse = roomStrategy.handleDraw(roomId);
            sendMessage("/room/" + roomId + "/game/stop", gameStopResponse);
        }
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