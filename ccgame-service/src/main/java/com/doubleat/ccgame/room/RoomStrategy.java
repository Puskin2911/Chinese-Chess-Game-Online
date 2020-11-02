package com.doubleat.ccgame.room;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.room.Room;

/**
 * @author Hop Nguyen
 */
public interface RoomStrategy {

    Room playerJoinRoom(UserDto userDto);

    boolean playerLeaveRoom(UserDto userDto, int roomId);

    Room playerJoinRoom(UserDto userDto, int roomId);

    void updatePlayerReady(String username, int roomId, boolean ready);

    boolean startGame(int roomId);
}
