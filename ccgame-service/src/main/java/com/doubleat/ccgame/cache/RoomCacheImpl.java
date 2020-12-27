package com.doubleat.ccgame.cache;

import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.exception.RoomIsFullPlayersException;
import com.doubleat.ccgame.exception.RoomNotFoundException;
import com.doubleat.ccgame.room.Room;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
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
        Room room = createNewRoom();
        room.getPlayers().add(userDto);

        synchronized (roomMap) {
            roomMap.put(room.getId(), room);
        }

        return room;
    }

    @Override
    public Room addPlayerToRoom(UserDto userDto, int roomId) {
        assert userDto != null;

        synchronized (roomMap) {
            Room room = roomMap.get(roomId);
            if (room == null)
                throw new RoomNotFoundException("Have no room have id: " + roomId);

            Set<UserDto> players = room.getPlayers();
            if (players.size() == 2)
                throw new RoomIsFullPlayersException("Room have id: " + roomId + " is full");

            players.add(userDto);
            return room;
        }
    }

    @Override
    public boolean removePlayerFromRoom(UserDto userDto, int roomId) {
        assert userDto != null;

        synchronized (roomMap) {
            Room room = roomMap.get(roomId);
            if (room == null)
                throw new RoomNotFoundException("Have no room have id: " + roomId);

            Set<UserDto> players = room.getPlayers();
            players.removeIf(player -> player.getUsername().equals(userDto.getUsername()));

            return !room.isGameOver();
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
            if (room == null)
                throw new RoomNotFoundException("Have no room have id: " + roomId);

            Set<UserDto> viewers = room.getViewers();
            viewers.removeIf(viewerInSet -> viewerInSet.getUsername().equals(viewer.getUsername()));
        }
    }

    /**
     * Get a available room if have. Other wise, create new room.
     *
     * @return A available room.
     */
    @Override
    public Room getOrCreateAvailableRoom() {
        synchronized (roomMap) {
            for (Map.Entry<Integer, Room> entry : roomMap.entrySet()) {
                Room room = entry.getValue();
                if (room.getPlayers().size() < 2)
                    return room;
            }
        }

        // All room is not available, create new one
        Room room = createNewRoom();

        synchronized (roomMap) {
            roomMap.put(room.getId(), room);
        }

        return room;
    }

    /**
     * {@inheritDoc}
     */
    @Override public void kickOutPlayer(UserDto userDto) {
        synchronized (roomMap) {
            for (Map.Entry<Integer, Room> entry : roomMap.entrySet()) {
                Room room = entry.getValue();
                Set<UserDto> players = room.getPlayers();
                players.removeIf(player -> player.getUsername().equals(userDto.getUsername()));
            }
        }
    }

    private Room createNewRoom() {
        int roomId = atomicId.getAndIncrement();
        // TODO: check conflict id.
        if (roomId == Integer.MAX_VALUE - 1) {
            atomicId.set(0);
        }
        return new Room(roomId);
    }

    @Override
    public UserDto getUserByName(String username, Integer roomID) {
        Room room = getRoomById(roomID);
        Iterator<UserDto> iterator = room.getPlayers().iterator();
        while (iterator.hasNext()){
            UserDto user = iterator.next();
            if(user != null && user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
}
