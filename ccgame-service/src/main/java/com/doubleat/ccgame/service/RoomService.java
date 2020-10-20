package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.model.Room;

public interface RoomService {
    Room joinRoom(UserDto userDto);
}
