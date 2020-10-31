package com.doubleat.ccgame.cache;

import com.doubleat.ccgame.dto.common.Player;
import com.doubleat.ccgame.exception.RoomNotFoundException;
import com.doubleat.ccgame.dto.common.Room;

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
     * @throws RoomNotFoundException if have no room ave specific id.
     */
    Room getRoomById(Integer roomId) throws RoomNotFoundException;

    /**
     * Add specific player to any available room.
     *
     * @param player player need to add to room.
     * @return the room just added player.
     */
    Room addPlayerToRoom(Player player);

    /**
     * Add specific player to room have specific id.
     *
     * @param player player need to add to room.
     * @param roomId id of the room, where need to add player.
     * @return the room just added player.
     */
    Room addPlayerToRoom(Player player, int roomId) throws RoomNotFoundException;

    /**
     * Remove specific player from a room have specific id.
     *
     * @param player player need to remove from the room.
     * @param roomId id of the room, where need to remove player.
     * @throws RoomNotFoundException if have no room have {@code roomId}.
     */
    void removePlayerFromRoom(Player player, int roomId) throws RoomNotFoundException;

    /**
     * Add specific viewer to room which have specific id.
     *
     * @param viewer viewer need to add to room.
     * @param roomId id of room, where need to add viewer.
     * @return the room just added viewer.
     * @throws RoomNotFoundException if have no room have {@code roomId}.
     */
    Room addViewerToRoom(Player viewer, int roomId) throws RoomNotFoundException;

    /**
     * Remove specific viewer from a room have specific id.
     *
     * @param viewer viewer need to remove from the room.
     * @param roomId id of the room, where need to remove viewer.
     * @throws RoomNotFoundException if have no room have {@code roomId}.
     */
    void removeViewerFromRoom(Player viewer, int roomId) throws RoomNotFoundException;
}