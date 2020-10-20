package com.doubleat.ccgame.service;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.model.Room;
import com.doubleat.ccgame.utils.RoomUtils;
import org.springframework.stereotype.Service;


@Service
public class RoomServiceImpl implements RoomService {
    @Override
    public Room joinRoom(UserDto userDto) {
        return RoomUtils.addUserToRoom(userDto);
    }
}
