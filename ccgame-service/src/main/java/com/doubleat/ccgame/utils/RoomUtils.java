package com.doubleat.ccgame.utils;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.exception.RoomIsFullPlayersException;
import com.doubleat.ccgame.exception.RoomNotFoundException;
import com.doubleat.ccgame.room.Room;

/**
 * @author Hop Nguyen
 */
public final class RoomUtils {

    private RoomUtils() {
    }

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
        if (room.getRedUserDto() != null) totals++;
        if (room.getBlackUserDto() != null) totals++;

        return totals;
    }

    /**
     * Add specific player to specific room.
     *
     * @param userDto the player need to add to room.
     * @param room    the room need to add player.
     * @throws RoomNotFoundException      if {@code room} is null.
     * @throws RoomIsFullPlayersException if {@code room} is full players.
     */
    public static void addPlayerToRoom(UserDto userDto, Room room) throws RoomNotFoundException, RoomIsFullPlayersException {
        if (room == null) throw new RoomNotFoundException();
        if (getCurrentPlayers(room) > 2) throw new RoomIsFullPlayersException();

        if (room.getRedUserDto() == null) room.setRedUserDto(userDto);
        else room.setBlackUserDto(userDto);
    }

    /**
     * Remove specific player to specific room.
     *
     * @param userDto the player need to remove to room.
     * @param room    the room need to remove player.
     * @throws RoomNotFoundException if {@code room} is null.
     */
    public static void removePlayerFromRoom(UserDto userDto, Room room) throws RoomNotFoundException {
        if (room == null) throw new RoomNotFoundException();

        UserDto redUserDto = room.getRedUserDto();
        if (redUserDto.equals(userDto)) {
            room.setRedUserDto(null);
            return;
        }

        UserDto blackUserDto = room.getBlackUserDto();
        if (blackUserDto.equals(userDto)) {
            room.setBlackUserDto(null);
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

    /**
     * Return number of ready players in specific room.
     *
     * @param room room need to return number of ready players.
     * @return number of ready players in specific room.
     * @throws RoomNotFoundException if {@code room} is null.
     */
    public static int getReadyPlayers(Room room) throws RoomNotFoundException {
        if (room == null) throw new RoomNotFoundException();

        int readyPlayers = 0;
        if (room.getBlackUserDto() != null && room.getBlackUserDto().isReady()) readyPlayers++;
        if (room.getRedUserDto() != null && room.getRedUserDto().isReady()) readyPlayers++;

        return readyPlayers;
    }

    /**
     * Update specific ready player to {@code isReady} in specific room.
     *
     * @param username username of player need to update {@code ready}.
     * @param isReady  state to update for use. {@code true} or {@code false}.
     * @param room     room where have player.
     * @throws RoomNotFoundException if {@code room} is null.
     */
    public static void updateReadyPlayerInRoom(String username, boolean isReady, Room room) throws RoomNotFoundException {
        assert username != null;

        UserDto redUserDto = room.getRedUserDto();
        if (redUserDto != null && redUserDto.getUsername().equals(username)) {
            redUserDto.setReady(isReady);
        } else {
            UserDto blackUserDto = room.getBlackUserDto();
            if (blackUserDto != null && blackUserDto.getUsername().equals(username)) {
                blackUserDto.setReady(isReady);
            }
        }
    }

}
