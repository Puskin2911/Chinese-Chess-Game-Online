package com.doubleat.ccgame.room;

import com.doubleat.ccgame.cache.RoomCache;
import com.doubleat.ccgame.domain.Game;
import com.doubleat.ccgame.domain.User;
import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.dto.converter.RoomConverter;
import com.doubleat.ccgame.dto.converter.UserConverter;
import com.doubleat.ccgame.dto.message.MoveMessage;
import com.doubleat.ccgame.dto.response.GameStopResponse;
import com.doubleat.ccgame.dto.response.PlayingGameDto;
import com.doubleat.ccgame.dto.response.RoomDto;
import com.doubleat.ccgame.game.Player;
import com.doubleat.ccgame.game.PlayingGame;
import com.doubleat.ccgame.repository.GameRepository;
import com.doubleat.ccgame.repository.UserRepository;
import com.doubleat.ccgame.utils.RoomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Hop Nguyen
 */
@Service
public class RoomStrategyImpl implements RoomStrategy {

    private static final Logger logger = LoggerFactory.getLogger(RoomStrategyImpl.class);

    @Autowired
    private RoomCache roomCache;

    @Autowired
    private RoomConverter roomConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public RoomDto playerJoinRoom(UserDto userDto) {
        Room room = roomCache.addPlayerToRoom(userDto);

        return roomConverter.toDto(room);
    }

    @Override
    public boolean playerLeaveRoom(UserDto userDto, int roomId) {
        return roomCache.removePlayerFromRoom(userDto, roomId);
    }

    @Override
    public RoomDto playerJoinRoom(UserDto userDto, int roomId) {
        Room room = roomCache.addPlayerToRoom(userDto, roomId);
        return roomConverter.toDto(room);
    }

    @Override
    public void updatePlayerReady(String username, int roomId, boolean ready) {
        Room room = roomCache.getRoomById(roomId);

        RoomUtils.updateReadyPlayerInRoom(username, ready, room);
    }

    @Override
    public Optional<PlayingGameDto> startGame(int roomId) {
        Room room = roomCache.getRoomById(roomId);

        int readyPlayers = RoomUtils.getReadyPlayers(room);

        logger.info("Player ready in room: " + readyPlayers);

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
            GameStopResponse gameStopResponse = GameStopResponse.builder()
                    .build();
            return Optional.of(gameStopResponse);
        } else
            return Optional.empty();
    }

    @Override
    public GameStopResponse handleStopGame(Integer roomId, String winner, String loser) {
        Room room = roomCache.getRoomById(roomId);
        UserDto winnerDto = null;
        UserDto loserDto = null;

        if (winner == null) {
            winner = room.getPlayers()
                    .stream()
                    .map(UserDto::getUsername)
                    .collect(Collectors.toList())
                    .get(0);
        }

        Optional<User> winnerOptional = userRepository.findByUsername(winner);
        Optional<User> loserOptional = userRepository.findByUsername(loser);

        if (winnerOptional.isPresent() && loserOptional.isPresent()) {
            User winnerUser = winnerOptional.get();
            winnerUser.setElo(winnerUser.getElo() + 12);
            User loserUser = loserOptional.get();
            loserUser.setElo(loserUser.getElo() - 12);

            winnerDto = userConverter.toDto(winnerUser);
            loserDto = userConverter.toDto(loserUser);

            Game game = new Game();
            game.setLoser(loserUser);
            game.setWinner(winnerUser);
            gameRepository.save(game);
        }

        assert winnerDto != null;
        room.setPlayers(new HashSet<>(Arrays.asList(winnerDto, loserDto)));

        RoomDto roomDto = RoomDto.builder()
                .id(roomId)
                .players(room.getPlayers())
                .viewers(room.getViewers())
                .build();

        return GameStopResponse.builder()
                .winner(winnerDto)
                .loser(loserDto)
                .roomDto(roomDto)
                .build();
    }

}
