package com.doubleat.ccgame.room;

import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.room.Room;

/**
 * @author Hop Nguyen
 * @version "1.0, 10/30/2020"
 */
public interface RoomCache {

    /**
     * Get room by room's id.
     *
     * @param roomId id of room need to get.
     * @return a room.
     */
    Room getRoomById(Integer roomId);

    /**
     * Add specific player to any available room.
     *
     * @param userDto player need to add to room.
     * @return the room just added player.
     */
    Room addPlayerToRoom(UserDto userDto);

    /**
     * Add specific player to room have specific id.
     *
     * @param userDto player need to add to room.
     * @param roomId  id of the room, where need to add player.
     * @return the room just added player.
     */
    Room addPlayerToRoom(UserDto userDto, int roomId);

    /**
     * Remove specific player from a room have specific id.
     *
     * @param userDto player need to remove from the room.
     * @param roomId  id of the room, where need to remove player.
     * @return {@code true} if leave room when game is not over.
     */
    boolean removePlayerFromRoom(UserDto userDto, int roomId);

    boolean removePlayerFromRoom(String username, int roomId);

    /**
     * Add specific viewer to room which have specific id.
     *
     * @param viewer viewer need to add to room.
     * @param roomId id of room, where need to add viewer.
     * @return the room just added viewer.
     */
    Room addViewerToRoom(UserDto viewer, int roomId);

    /**
     * Remove specific viewer from a room have specific id.
     *
     * @param viewer viewer need to remove from the room.
     * @param roomId id of the room, where need to remove viewer.
     */
    void removeViewerFromRoom(UserDto viewer, int roomId);

    /**
     * Get a available room if have. Other wise, create new room.
     *
     * @return A available room.
     */
    Room getOrCreateAvailableRoom();

    /**
     * Kick out a specific player from all room.
     *
     * @param userDto Player need to kick out side room.
     */
    void kickOutPlayer(UserDto userDto);

}
