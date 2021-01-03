package com.doubleat.ccgame.room;

import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.dto.message.MoveMessage;
import com.doubleat.ccgame.dto.response.PlayingGameDto;
import com.doubleat.ccgame.dto.response.GameStopResponse;
import com.doubleat.ccgame.dto.response.RoomDto;

import java.util.Optional;

/**
 * @author Hop Nguyen
 */
public interface RoomStrategy {

    /**
     * Add player join one available room.
     *
     * @param userDto player need to add to room.
     * @return the {@code RoomDto} object which added player.
     */
    RoomDto playerJoinRoom(UserDto userDto);

    /**
     * Remove specific player form specific room.
     *
     * @param userDto player need to remove from room.
     * @param roomId  id of room, which need to remove player.
     * @return {@code true}  if remove successfully, other wise return {@code false}.
     */
    boolean playerLeaveRoom(UserDto userDto, int roomId);

    boolean playerLeaveRoom(String username, int roomId);

    /**
     * Add player join a specific room.
     *
     * @param userDto player need to add to room.
     * @param roomId  id of room, which need to add player
     * @return the room which added player.
     */
    RoomDto playerJoinRoom(UserDto userDto, int roomId);

    /**
     * Update {@code ready} state of player in specific room.
     *
     * @param username username of player need to update {@code ready} state.
     * @param roomId   id of room, which need to update player {@code ready} state.
     * @param ready    {@code ready} state need to update.
     *                 {@code true} if player is ready or {@code false} if the player is not ready yet.
     */
    void updatePlayerReady(String username, int roomId, boolean ready);

    /**
     * Start game in one specific room.
     *
     * @param roomId id of room, which need to start game.
     */
    Optional<PlayingGameDto> startGame(int roomId);

    /**
     * @return A available room.
     */
    RoomDto getAvailableRoom();

    /**
     * Kick out a specific player from all room.
     *
     * @param userDto Player need to kick out from room.
     */
    void kickOutPlayer(UserDto userDto);

    PlayingGameDto handleMove(MoveMessage move, String username, Integer roomId);

    /**
     * Check if game in a specific room is over or not.
     *
     * @param roomId id of room need to check.
     * @return {@code Optional<GameStopResponse>} if game is over. Other wise, return {@code Optional.empty()}
     */
    Optional<GameStopResponse> handleGameOver(Integer roomId);

    GameStopResponse handleForceLeaveRoom(Integer roomId, String loser);

    GameStopResponse handleSurrenderRequest(Integer roomId, String loserUsername);

    GameStopResponse handleDraw(Integer roomId);

}
