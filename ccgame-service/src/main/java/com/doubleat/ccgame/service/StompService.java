package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.dto.message.MoveMessage;
import com.doubleat.ccgame.dto.message.ReadyMessage;
import com.doubleat.ccgame.dto.response.GameStopResponse;
import org.springframework.scheduling.annotation.Async;

public interface StompService {
    void handlePlayerReady(ReadyMessage message, Integer roomId, String name);

    void handleMove(MoveMessage move, String username, Integer roomId);

    void notifyPlayerLeaveRoom(int roomId, UserDto userDto);

    void notifyPlayerJoinRoom(int roomId, UserDto userDto);

    void notifyStopGame(int roomId, GameStopResponse gameStopResponse);

}
