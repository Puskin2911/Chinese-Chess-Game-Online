package com.doubleat.ccgame.utils;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.exception.RoomIsFullPlayersException;
import com.doubleat.ccgame.exception.RoomNotFoundException;
import com.doubleat.ccgame.model.Room;

/**
 * @author Hop Nguyen
 */
public final class RoomUtils {

    /**
     * Return current players in specific room.
     *
     * @param room room need to get current players.
     * @return current players in specific room.
     * @throws RoomNotFoundException if {@code room} is null
     */
    public static int getCurrentPlayers(Room room) throws RoomNotFoundException {
        if (room == null) throw new RoomNotFoundException();

        int totals = 0;
        if (room.getRedPlayer() != null) totals++;
        if (room.getBlackPlayer() != null) totals++;

        return totals;
    }

    /**
     * Add specific player to specific room.
     *
     * @param player the player need to add to room.
     * @param room   the room need to add player.
     * @throws RoomNotFoundException      if {@code room} is null.
     * @throws RoomIsFullPlayersException if {@code room} is full players.
     */
    public static void addPlayerToRoom(UserDto player, Room room) throws RoomNotFoundException, RoomIsFullPlayersException {
        if (room == null) throw new RoomNotFoundException();
        if (getCurrentPlayers(room) > 2) throw new RoomIsFullPlayersException();

        if (room.getRedPlayer() == null) room.setRedPlayer(player);
        else room.setBlackPlayer(player);
    }

    /**
     * Remove specific player to specific room.
     *
     * @param player the player need to remove to room.
     * @param room   the room need to remove player.
     * @throws RoomNotFoundException if {@code room} is null.
     */
    public static void removePlayerFromRoom(UserDto player, Room room) throws RoomNotFoundException {
        if (room == null) throw new RoomNotFoundException();

        UserDto redUser = room.getRedPlayer();
        if (redUser == player) {
            room.setRedPlayer(null);
            return;
        }

        UserDto blackUser = room.getBlackPlayer();
        if (blackUser == player) {
            room.setBlackPlayer(null);
        }
    }

    /**
     * Add specific viewer to specific room.
     *
     * @param viewer the viewer need to add to room.
     * @param room   the room need to add viewer.
     * @throws RoomNotFoundException if {@code room} is null.
     */
    public static void addViewerToRoom(UserDto viewer, Room room) throws RoomNotFoundException {
        if (room == null) throw new RoomNotFoundException();
        room.getViewers().add(viewer);
    }

    /**
     * Remove specific viewer to specific room.
     *
     * @param viewer the viewer need to remove to room.
     * @param room   the room need to remove viewer.
     * @throws RoomNotFoundException if {@code room} is null.
     */
    public static void removeViewerFromRoom(UserDto viewer, Room room) throws RoomNotFoundException {
        if (room == null) throw new RoomNotFoundException();
        room.getViewers().remove(viewer);
    }
}
