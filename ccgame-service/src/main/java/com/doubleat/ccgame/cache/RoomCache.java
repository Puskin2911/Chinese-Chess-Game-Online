package com.doubleat.ccgame.cache;

import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.exception.RoomNotFoundException;
import com.doubleat.ccgame.model.Room;

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
     * @param player player need to add to room.
     * @return the room just added player.
     */
    Room addPlayerToRoom(UserDto player);


    /**
     * Add specific player to room have specific id.
     *
     * @param player player need to add to room.
     * @param roomId id of the room, where need to add player.
     * @return the room just added player.
     */
    Room addPlayerToRoom(UserDto player, int roomId) throws RoomNotFoundException;

    /**
     * Remove specific player from a room have specific id.
     *
     * @param player player need to remove from the room.
     * @param roomId id of the room, where need to remove player.
     * @throws RoomNotFoundException if have no room have {@code roomId}.
     */
    void removePlayerFromRoom(UserDto player, int roomId) throws RoomNotFoundException;

    /**
     * Add specific viewer to room which have specific id.
     *
     * @param viewer viewer need to add to room.
     * @param roomId id of room, where need to add viewer.
     * @return the room just added viewer.
     * @throws RoomNotFoundException if have no room have {@code roomId}.
     */
    Room addViewerToRoom(UserDto viewer, int roomId) throws RoomNotFoundException;

    /**
     * Remove specific viewer from a room have specific id.
     *
     * @param viewer viewer need to remove from the room.
     * @param roomId id of the room, where need to remove viewer.
     * @throws RoomNotFoundException if have no room have {@code roomId}.
     */
    void removeViewerFromRoom(UserDto viewer, int roomId) throws RoomNotFoundException;
}
