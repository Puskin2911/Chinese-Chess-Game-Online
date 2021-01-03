package com.doubleat.ccgame.room;

import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.exception.RoomIsFullPlayersException;
import com.doubleat.ccgame.exception.RoomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Hop Nguyen
 * @version "1.0, 10/30/2020"
 */
@Component
public class RoomCacheImpl implements RoomCache {

    private final Map<Integer, Room> roomMap = new HashMap<>();

    private final AtomicInteger atomicId = new AtomicInteger();

    private static final String ROOM_NOT_FOUND_EXCEPTION_MESSAGE = "Not found room id: ";

    @Override
    public Room getRoomById(Integer roomId) {
        synchronized (roomMap) {
            return roomMap.get(roomId);
        }
    }

    @Override
    public Room addPlayerToRoom(UserDto userDto) {
        synchronized (roomMap) {
            for (Map.Entry<Integer, Room> entry : roomMap.entrySet()) {
                Room room = entry.getValue();
                Set<UserDto> players = room.getPlayers();

                List<UserDto> playerList = new ArrayList<>(players);
                for (UserDto user : playerList) {
                    if (user.getUsername().equals(userDto.getUsername())) {
                        playerList.remove(user);
                        playerList.add(userDto);
                        room.setPlayers(new HashSet<>(playerList));
                        return room;
                    }
                }

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
    public synchronized Room addPlayerToRoom(UserDto userDto, int roomId) {
        Room room = roomMap.get(roomId);
        if (room == null)
            throw new RoomNotFoundException(ROOM_NOT_FOUND_EXCEPTION_MESSAGE + roomId);

        Set<UserDto> players = room.getPlayers();
        if (players.size() == 2)
            throw new RoomIsFullPlayersException("Room have id: " + roomId + " is full");

        players.add(userDto);
        return room;
    }

    @Override
    public boolean removePlayerFromRoom(UserDto userDto, int roomId) {
        return removePlayerFromRoom(userDto.getUsername(), roomId);
    }

    @Override
    public synchronized boolean removePlayerFromRoom(String username, int roomId) {
        Room room = roomMap.get(roomId);
        if (room == null)
            throw new RoomNotFoundException(ROOM_NOT_FOUND_EXCEPTION_MESSAGE + roomId);

        Set<UserDto> players = room.getPlayers();
        players.removeIf(player -> player.getUsername().equals(username));

        return !room.isGameOver();
    }

    @Override
    public synchronized Room addViewerToRoom(UserDto viewer, int roomId) {
        Room room = roomMap.get(roomId);
        room.getPlayers().add(viewer);
        return room;
    }

    @Override
    public synchronized void removeViewerFromRoom(UserDto viewer, int roomId) {
        Room room = roomMap.get(roomId);
        if (room == null)
            throw new RoomNotFoundException(ROOM_NOT_FOUND_EXCEPTION_MESSAGE + roomId);

        Set<UserDto> viewers = room.getViewers();
        viewers.removeIf(viewerInSet -> viewerInSet.getUsername().equals(viewer.getUsername()));
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

}
