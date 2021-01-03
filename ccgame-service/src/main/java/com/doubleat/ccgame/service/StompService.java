package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.dto.message.MoveMessage;
import com.doubleat.ccgame.dto.message.ReadyMessage;
import com.doubleat.ccgame.dto.response.GameStopResponse;

public interface StompService {

    void handlePlayerReady(ReadyMessage message, Integer roomId, String name);

    void handleMove(MoveMessage move, String username, Integer roomId);

    void notifyPlayerLeaveRoom(int roomId, UserDto userDto);

    void notifyPlayerJoinRoom(int roomId, UserDto userDto);

    void notifyStopGame(int roomId, GameStopResponse gameStopResponse);

    void handleSurrenderRequest(Integer roomId, String loserUsername);

    void handleTimeOver(Integer roomId, String loserUsername);

    void handleDrawResponse(Integer roomId, boolean isAgree, String name);

}
