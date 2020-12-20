package com.doubleat.ccgame.room;

import com.doubleat.ccgame.cache.RoomCache;
import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.dto.converter.RoomConverter;
import com.doubleat.ccgame.dto.response.RoomDto;
import com.doubleat.ccgame.utils.RoomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Hop Nguyen
 */
@Service
public class RoomStrategyImpl implements RoomStrategy {

    private static final Logger logger = LoggerFactory.getLogger(RoomStrategyImpl.class);

    private final RoomCache roomCache;
    private final RoomConverter roomConverter;

    @Autowired
    public RoomStrategyImpl(RoomCache roomCache, RoomConverter roomConverter) {
        this.roomCache = roomCache;
        this.roomConverter = roomConverter;
    }

    @Override
    public RoomDto playerJoinRoom(UserDto userDto) {
        Room room = roomCache.addPlayerToRoom(userDto);

        return roomConverter.toDto(room);
    }

    @Override
    public boolean playerLeaveRoom(UserDto userDto, int roomId) {
        roomCache.removePlayerFromRoom(userDto, roomId);
        return true;
    }

    @Override
    public RoomDto playerJoinRoom(UserDto userDto, int roomId) {
        Room room = roomCache.addPlayerToRoom(userDto, roomId);
        return roomConverter.toDto(room);
    }

    @Override
    public boolean updatePlayerReady(String username, int roomId, boolean ready) {
        logger.info("Starting update player ready..");

        Room room = roomCache.getRoomById(roomId);

        RoomUtils.updateReadyPlayerInRoom(username, ready, room);

        logger.info("Just update player ready in room!");

        return RoomUtils.getReadyPlayers(room) == 2;
    }

    @Override
    public boolean startGame(int roomId) {
        Room room = roomCache.getRoomById(roomId);

        return RoomUtils.getReadyPlayers(room) == 2;
    }

    /**
     * @return A available room.
     */
    @Override
    public RoomDto getAvailableRoom() {
        Room room = roomCache.getOrCreateAvailableRoom();
        return roomConverter.toDto(room);
    }

    /**
     * {@inheritDoc}
     */
    @Override public void kickOutPlayer(UserDto userDto) {
        roomCache.kickOutPlayer(userDto);
    }

}
