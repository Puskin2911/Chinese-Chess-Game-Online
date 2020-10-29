package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.model.Room;

/**
 * @author Hop Nguyen
 */
public interface RoomService {
    Room joinRoom(UserDto userDto);

    Boolean leaveRoom(int roomId, UserDto userDto);

    Room joinRoom(UserDto userDto, int roomId);
}
