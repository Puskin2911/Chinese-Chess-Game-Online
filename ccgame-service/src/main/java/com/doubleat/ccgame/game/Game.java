package com.doubleat.ccgame.game;

import lombok.Getter;
import lombok.Setter;

/**
 * Chinese Chess Game.
 *
 * @author Hop Nguyen
 */
@Getter
@Setter
public class Game {

    private Player redPlayer;

    private Player blackPlayer;

    private Board board;

    private boolean redTurn;

    /**
     * @param redPlayer   red player of game.
     * @param blackPlayer black player of game.
     */
    public Game(Player redPlayer, Player blackPlayer) {
        this.redPlayer = redPlayer;
        this.blackPlayer = blackPlayer;
    }

    /**
     * Start new game.
     */
    public void start() {
        board = new Board();
        redTurn = true;
    }

}
