package com.doubleat.ccgame.room;

import com.doubleat.ccgame.cache.RoomCache;
import com.doubleat.ccgame.dto.common.UserDto;
import com.doubleat.ccgame.dto.converter.RoomConverter;
import com.doubleat.ccgame.dto.message.MoveMessage;
import com.doubleat.ccgame.dto.response.PlayingGameDto;
import com.doubleat.ccgame.dto.response.GameStopResponse;
import com.doubleat.ccgame.dto.response.RoomDto;
import com.doubleat.ccgame.game.Player;
import com.doubleat.ccgame.game.PlayingGame;
import com.doubleat.ccgame.utils.RoomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Hop Nguyen
 */
@Service
public class RoomStrategyImpl implements RoomStrategy {

    private static final Logger logger = LoggerFactory.getLogger(RoomStrategyImpl.class);

    private final RoomCache roomCache;
    private final RoomConverter roomConverter;

    @Autowired
    public RoomStrategyImpl(RoomCache roomCache, RoomConverter roomConverter) {
        this.roomCache = roomCache;
        this.roomConverter = roomConverter;
    }

    @Override
    public RoomDto playerJoinRoom(UserDto userDto) {
        Room room = roomCache.addPlayerToRoom(userDto);

        return roomConverter.toDto(room);
    }

    @Override
    public boolean playerLeaveRoom(UserDto userDto, int roomId) {
        roomCache.removePlayerFromRoom(userDto, roomId);
        return true;
    }

    @Override
    public RoomDto playerJoinRoom(UserDto userDto, int roomId) {
        Room room = roomCache.addPlayerToRoom(userDto, roomId);
        return roomConverter.toDto(room);
    }

    @Override
    public void updatePlayerReady(String username, int roomId, boolean ready) {
        logger.info("Starting update player ready..");

        Room room = roomCache.getRoomById(roomId);

        RoomUtils.updateReadyPlayerInRoom(username, ready, room);

        logger.info("Just update player ready in room!");
    }

    @Override
    public Optional<PlayingGameDto> startGame(int roomId) {
        Room room = roomCache.getRoomById(roomId);

        int readyPlayers = RoomUtils.getReadyPlayers(room);

        if (readyPlayers == 2) {
            List<UserDto> players = new ArrayList<>(room.getPlayers());

            UserDto firstPlayer = players.get(0);
            UserDto secondPlayer = players.get(1);

            Player redPlayer = new Player(firstPlayer.getUsername(), true);
            Player blackPlayer = new Player(secondPlayer.getUsername(), false);

            PlayingGame playingGame = PlayingGame.builder()
                    .redPlayer(redPlayer)
                    .blackPlayer(blackPlayer)
                    .build();

            // Start new game to setup
            playingGame.start();

            room.setPlayingGame(playingGame);

            PlayingGameDto playingGameDto = PlayingGameDto.builder()
                    .boardStatus(playingGame.getBoardStatus())
                    .nextTurnUsername(redPlayer.getUsername())
                    .redPlayerUsername(playingGame.getRedPlayer().getUsername())
                    .build();

            return Optional.of(playingGameDto);
        }

        return Optional.empty();
    }

    /**
     * @return A available room.
     */
    @Override
    public RoomDto getAvailableRoom() {
        Room room = roomCache.getOrCreateAvailableRoom();
        return roomConverter.toDto(room);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void kickOutPlayer(UserDto userDto) {
        roomCache.kickOutPlayer(userDto);
    }

    @Override
    public PlayingGameDto handleMove(MoveMessage move, String username, Integer roomId) {
        Room room = roomCache.getRoomById(roomId);

        PlayingGame playingGame = room.getPlayingGame();
        Player playerMove = playingGame.getPlayerByUsername(username);

        boolean isMoved = playingGame.doMove(playerMove, move.getMoveString());

        return PlayingGameDto.builder()
                .boardStatus(playingGame.getBoardStatus())
                .nextTurnUsername(playingGame.getNextTurnUsername())
                .redPlayerUsername(playingGame.getRedPlayer().getUsername())
                .build();
    }

    @Override
    public Optional<GameStopResponse> isGameOver(Integer roomId) {
        Room room = roomCache.getRoomById(roomId);

        if (room.isGameOver()) {
            GameStopResponse gameStopResponse = new GameStopResponse();
            gameStopResponse.setRedWin(room.isRedWin());
            return Optional.of(gameStopResponse);
        } else
            return Optional.empty();
    }

}
