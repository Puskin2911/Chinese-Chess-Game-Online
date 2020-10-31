package com.doubleat.ccgame.service;

import com.doubleat.ccgame.cache.RoomCache;
import com.doubleat.ccgame.dto.common.Player;
import com.doubleat.ccgame.dto.common.Room;
import com.doubleat.ccgame.utils.RoomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Hop Nguyen
 */
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomCache roomCache;

    @Autowired
    public RoomServiceImpl(RoomCache roomCache) {
        this.roomCache = roomCache;
    }

    @Override
    public Room playerJoinRoom(Player player) {
        return roomCache.addPlayerToRoom(player);
    }

    @Override
    public boolean playerLeaveRoom(Player player, int roomId) {
        roomCache.removePlayerFromRoom(player, roomId);
        return true;
    }

    @Override
    public Room playerJoinRoom(Player player, int roomId) {
        return roomCache.addPlayerToRoom(player, roomId);
    }

    @Override
    public void updatePlayerReady(String username, int roomId, boolean ready) {
        Room room = roomCache.getRoomById(roomId);

        RoomUtils.updateReadyPlayerInRoom(username, true, room);
    }

    @Override
    public boolean startGame(int roomId) {
        Room room = roomCache.getRoomById(roomId);

        return RoomUtils.getReadyPlayers(room) == 2;
    }

}
