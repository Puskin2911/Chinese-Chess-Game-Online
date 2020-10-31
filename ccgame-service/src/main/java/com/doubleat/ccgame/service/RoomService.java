package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.common.Player;
import com.doubleat.ccgame.dto.common.Room;

/**
 * @author Hop Nguyen
 */
public interface RoomService {

    Room playerJoinRoom(Player player);

    boolean playerLeaveRoom(Player player, int roomId);

    Room playerJoinRoom(Player player, int roomId);

    void updatePlayerReady(String username, int roomId, boolean ready);

    boolean startGame(int roomId);
}
