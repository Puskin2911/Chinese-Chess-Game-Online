package com.doubleat.ccgame.cache;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.exception.RoomIsFullPlayersException;
import com.doubleat.ccgame.exception.RoomNotFoundException;
import com.doubleat.ccgame.room.Room;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
    public Room addPlayerToRoom(UserDto userDto) {
        assert userDto != null;

        synchronized (roomMap) {
            for (Map.Entry<Integer, Room> entry : roomMap.entrySet()) {
                Room room = entry.getValue();
                Set<UserDto> players = room.getPlayers();

                if (players.size() < 2) {
                    players.add(userDto);
                    return room;
                }
            }
        }

        // All room is full, need create new room
        int roomId = atomicId.getAndIncrement();
        // TODO: check conflict id.
        if (roomId == Integer.MAX_VALUE - 1) {
            atomicId.set(0);
        }
        Room room = new Room(roomId);
        room.getPlayers().add(userDto);

        synchronized (roomMap) {
            roomMap.put(roomId, room);
        }

        return room;
    }

    @Override
    public Room addPlayerToRoom(UserDto userDto, int roomId) {
        assert userDto != null;

        synchronized (roomMap) {
            Room room = roomMap.get(roomId);
            if (room == null) throw new RoomNotFoundException("Have no room have id: " + roomId);

            Set<UserDto> players = room.getPlayers();
            if (players.size() == 2) throw new RoomIsFullPlayersException("Room have id: " + roomId + " is full");

            players.add(userDto);
            return room;
        }
    }

    @Override
    public void removePlayerFromRoom(UserDto userDto, int roomId) {
        assert userDto != null;

        synchronized (roomMap) {
            Room room = roomMap.get(roomId);
            if (room == null) throw new RoomNotFoundException("Have no room have id: " + roomId);

            Set<UserDto> players = room.getPlayers();
            players.removeIf(player -> player.getUsername().equals(userDto.getUsername()));
        }
    }

    @Override
    public Room addViewerToRoom(UserDto viewer, int roomId) throws RoomNotFoundException {
        assert viewer != null;

        synchronized (roomMap) {
            Room room = roomMap.get(roomId);
            room.getPlayers().add(viewer);
            return room;
        }
    }

    @Override
    public void removeViewerFromRoom(UserDto viewer, int roomId) throws RoomNotFoundException {
        assert viewer != null;

        synchronized (roomMap) {
            Room room = roomMap.get(roomId);
            if (room == null) throw new RoomNotFoundException("Have no room have id: " + roomId);

            Set<UserDto> viewers = room.getViewers();
            viewers.removeIf(viewerInSet -> viewerInSet.getUsername().equals(viewer.getUsername()));
        }
    }

}
