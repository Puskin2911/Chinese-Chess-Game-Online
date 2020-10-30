package com.doubleat.ccgame.service;

import com.doubleat.ccgame.cache.RoomCache;
import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.dto.common.Room;
import org.springframework.stereotype.Service;


/**
 * @author Hop Nguyen
 */
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomCache roomCache;

    public RoomServiceImpl(RoomCache roomCache) {
        this.roomCache = roomCache;
    }

    @Override
    public Room joinRoom(UserDto userDto) {
        return roomCache.addPlayerToRoom(userDto);
    }

    @Override
    public Boolean leaveRoom(int roomId, UserDto userDto) {
        roomCache.removePlayerFromRoom(userDto, roomId);
        return true;
    }

    @Override
    public Room joinRoom(UserDto userDto, int roomId) {
        return roomCache.addPlayerToRoom(userDto, roomId);
    }
}
