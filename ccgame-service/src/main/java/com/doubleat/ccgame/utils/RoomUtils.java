package com.doubleat.ccgame.utils;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.model.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hop Nguyen
 */
public final class RoomUtils {
    private static int id = 0;
    private static final List<Room> rooms = new ArrayList<>();

    public static boolean available(Room room) {
        return room.getRedUser() == null || room.getBlackUser() == null;
    }

    public static Room getRoomById(int roomId) {
        // TODO Verify roomId
        for (Room room : rooms) {
            if (room.getId() == roomId) {
                return room;
            }
        }
        return null;
    }

    public static Room addUserToRoom(UserDto user) {
        for (Room room : rooms) {
            if (available(room)) {
                if (room.getRedUser() == null) room.setRedUser(user);
                else room.setBlackUser(user);
                return room;
            }
        }
        // No room available
        Room room = new Room(id++);
        room.setRedUser(user);
        return room;
    }

    public static boolean removeUserToRoom(UserDto userDto) {
        // TODO check userDto null
        for (Room room : rooms) {
            UserDto redUser = room.getRedUser();
            if (redUser != null && redUser.getId() == userDto.getId()) {
                room.setRedUser(null);
                if (getCurrentPlayers(room) == 0) {
                    rooms.remove(room);
                }
                return true;
            }

            UserDto blackUser = room.getBlackUser();
            if (blackUser != null && blackUser.getId() == userDto.getId()) {
                room.setBlackUser(null);
                if (getCurrentPlayers(room) == 0) {
                    rooms.remove(room);
                }
                return true;
            }


        }

        return false;
    }

    public static Room addUserToRoom(UserDto userDto, int roomId) {
        for (Room room : rooms) {
            if (room.getId() == roomId) {
                if (room.getRedUser() == null) {
                    room.setRedUser(userDto);
                    return room;
                } else if (room.getBlackUser() == null) {
                    room.setBlackUser(userDto);
                    return room;
                } else {
                    // TODO
                }
            }
        }

        return null;
    }

    public static int getCurrentPlayers(Room room) {
        // TODO room not null
        int totals = 0;
        if (room.getRedUser() != null) totals++;
        if (room.getBlackUser() != null) totals++;

        return totals;
    }
}
