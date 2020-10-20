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
}
