package com.doubleat.ccgame.utils;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.room.Room;

/**
 * @author Hop Nguyen
 */
public final class RoomUtils {

    private RoomUtils() {
    }

    /**
     * Return number of ready players in specific room.
     *
     * @param room room need to return number of ready players.
     * @return number of ready players in specific room.
     */
    public static int getReadyPlayers(Room room) {
        assert room != null;

        int readyPlayers = 0;
        for (UserDto player : room.getPlayers()) {
            if (player.isReady()) readyPlayers++;
        }

        return readyPlayers;
    }

    /**
     * Update specific ready player to {@code isReady} in specific room.
     *
     * @param username username of player need to update {@code ready}.
     * @param isReady  state to update for use. {@code true} or {@code false}.
     * @param room     room where have player.
     */
    public static void updateReadyPlayerInRoom(String username, boolean isReady, Room room) {
        assert username != null;
        assert room != null;

        for (UserDto player : room.getPlayers()) {
            if (player.getUsername().equals(username)) {
                player.setReady(isReady);
                return;
            }
        }
    }

}
