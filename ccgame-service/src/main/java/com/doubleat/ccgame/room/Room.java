package com.doubleat.ccgame.room;

import com.doubleat.ccgame.dto.response.UserDto;
import com.doubleat.ccgame.game.GameCache;
import com.doubleat.ccgame.game.PlayingGame;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Room {
    @NonNull
    private int id;

    private Set<UserDto> players = new HashSet<>(2);

    private Set<UserDto> viewers = new HashSet<>();

    private PlayingGame playingGame;

    public boolean isGameOver() {
        if (playingGame == null)
            return true;
        return playingGame.isOver();
    }

    public GameCache resolveGame() {
        if (playingGame != null && playingGame.isOver()) {
            boolean isRedWin = playingGame.isRedWin();

            String winnerUsername;
            String loserUsername;

            if (isRedWin) {
                winnerUsername = playingGame.getRedPlayer().getUsername();
                loserUsername = playingGame.getBlackPlayer().getUsername();
            } else {
                winnerUsername = playingGame.getBlackPlayer().getUsername();
                loserUsername = playingGame.getRedPlayer().getUsername();
            }
            return new GameCache(winnerUsername, loserUsername);
        }
        return null;
    }

}
