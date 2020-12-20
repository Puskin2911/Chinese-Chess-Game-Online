package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.message.MoveMessage;
import com.doubleat.ccgame.dto.message.ReadyMessage;

public interface StompService {
    void handlePlayerReady(ReadyMessage message, Integer roomId, String name);

    void handleMove(MoveMessage move, String username, Integer roomId);
}
