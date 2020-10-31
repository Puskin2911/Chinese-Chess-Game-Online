package com.doubleat.ccgame.cache;

import com.doubleat.ccgame.dto.common.Player;
import com.doubleat.ccgame.exception.RoomNotFoundException;
import com.doubleat.ccgame.dto.common.Room;
import com.doubleat.ccgame.utils.RoomUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Hop Nguyen
 * @version "1.0, 10/30/2020"
 */
@Component
public class RoomCacheImpl implements RoomCache {

    private final Map<Integer, Room> roomMap = new HashMap<>();

    private final AtomicInteger atomicId = new AtomicInteger();

    @Override
    public Room getRoomById(Integer roomId) {
        synchronized (roomMap) {
            return roomMap.get(roomId);
        }
    }

    @Override
    public Room addPlayerToRoom(Player player) {
        assert player != null;

        synchronized (roomMap) {
            for (Map.Entry<Integer, Room> entry : roomMap.entrySet()) {
                Room room = entry.getValue();
                if (RoomUtils.getCurrentPlayers(room) < 2) {
                    if (room.getRedPlayer() == null) room.setRedPlayer(player);
                    else room.setBlackPlayer(player);
                    return room;
                }
            }
        }

        // All room is full, need create new room
        int roomId = atomicId.getAndIncrement();
        // TODO: check conflict id.
        if (roomId == Integer.MAX_VALUE - 1) {
            roomId = 0;
            atomicId.set(0);
        }
        Room room = new Room(roomId);
        room.setBlackPlayer(player);
        roomMap.put(roomId, room);

        return room;
    }

    @Override
    public Room addPlayerToRoom(Player player, int roomId) {
        assert player != null;

        synchronized (roomMap) {
            Room room = roomMap.get(roomId);
            RoomUtils.addPlayerToRoom(player, room);
            return room;
        }
    }

    @Override
    public void removePlayerFromRoom(Player player, int roomId) {
        assert player != null;

        synchronized (roomMap) {
            Room room = roomMap.get(roomId);

            RoomUtils.removePlayerFromRoom(player, room);
        }
    }

    @Override
    public Room addViewerToRoom(Player viewer, int roomId) throws RoomNotFoundException {
        assert viewer != null;

        synchronized (roomMap) {
            Room room = roomMap.get(roomId);
            RoomUtils.addViewerToRoom(viewer, room);
            return room;
        }
    }

    @Override
    public void removeViewerFromRoom(Player viewer, int roomId) throws RoomNotFoundException {
        assert viewer != null;

        synchronized (roomMap) {
            Room room = roomMap.get(roomId);
            RoomUtils.removeViewerFromRoom(viewer, room);
        }
    }

}
