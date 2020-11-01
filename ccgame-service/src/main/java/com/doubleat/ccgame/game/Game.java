package com.doubleat.ccgame.game;

import com.doubleat.ccgame.game.exception.InvalidMoveException;
import com.doubleat.ccgame.game.piece.Piece;
import com.doubleat.ccgame.game.utils.MoveUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Stack;

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

    private boolean playing;

    /**
     * Save all moves.
     */
    private Stack<String> moves;

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

    /**
     * @return game is over or not.
     */
    public boolean isOver() {
        return false;
    }

    public void doMove(Player player, String move) {
        assert player != null;

        if (!MoveUtils.isValidMove(move))
            throw new InvalidMoveException("Move is not valid format: [0-9][0-8]_[0-9][0-8]");
        if (player.isRed() != redTurn)
            throw new IllegalArgumentException("This turn is not for: " + player.getUsername());

        Position from = Position.getPositionFromString(move.substring(0, 2));
        Position to = Position.getPositionFromString(move.substring(3));

        Piece current = board.getPieceByPosition(from);

        if (current == null || !current.isValidMove(board, from, to))
            throw new InvalidMoveException("Invalid move");

        // Do move
        Piece[][] pieces = board.getPieces();
        pieces[from.getX()][from.getY()] = null;
        pieces[to.getX()][to.getY()] = current;

        redTurn = !redTurn;
        moves.push(move);
    }

}
