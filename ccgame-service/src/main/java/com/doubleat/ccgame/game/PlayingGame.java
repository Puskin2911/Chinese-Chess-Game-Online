package com.doubleat.ccgame.game;

import com.doubleat.ccgame.game.exception.InvalidMoveException;
import com.doubleat.ccgame.game.piece.Piece;
import com.doubleat.ccgame.game.utils.MoveUtils;
import lombok.*;

import java.util.Stack;

/**
 * Chinese Chess Game.
 *
 * @author Hop Nguyen
 */
@Data
@Builder
public class PlayingGame {

    @NonNull
    private Player redPlayer;

    @NonNull
    private Player blackPlayer;

    private Board board;

    private boolean isRedTurn;

    private boolean isOver;

    /**
     * Save all moves.
     */
    private Stack<String> moves;

    /**
     * Get player in game.
     *
     * @param username username of player need to get
     * @return Player
     */
    public Player getPlayerByUsername(String username) {
        if (redPlayer.getUsername().equals(username)) {
            return redPlayer;
        } else if (blackPlayer.getUsername().equals(username)) {
            return blackPlayer;
        }

        return null;
    }

    /**
     * Start new game.
     */
    public void start() {
        board = new Board();
        isRedTurn = true;
        isOver = false;
    }

    public boolean doMove(Player player, String move) {
        assert player != null;

        if (!MoveUtils.isValidMove(move) || player.isRed() != isRedTurn)
            return false;

        Position from = Position.getPositionFromString(move.substring(0, 2));
        Position to = Position.getPositionFromString(move.substring(3));

        Piece current = board.getPieceByPosition(from);

        if (current == null || !current.isValidMove(board, from, to))
            return false;

        // Do move
        Piece[][] pieces = board.getPieces();
        pieces[from.getX()][from.getY()] = null;
        pieces[to.getX()][to.getY()] = current;

        isRedTurn = !isRedTurn;
        moves.push(move);

        return true;
    }

    /**
     * @return Status of board in this game.
     */
    public String getBoardStatus() {
        return this.board.getBoardStatus();
    }

    /**
     * @return Username have next turn.
     */
    public String getNextTurnUsername() {
        if (redPlayer.isRed() == isRedTurn)
            return redPlayer.getUsername();
        else
            return blackPlayer.getUsername();
    }

}
