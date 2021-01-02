package com.doubleat.ccgame.game;

import com.doubleat.ccgame.game.exception.InvalidMoveException;
import com.doubleat.ccgame.game.piece.Piece;
import com.doubleat.ccgame.game.utils.GameUtils;
import com.doubleat.ccgame.game.utils.MoveUtils;
import com.doubleat.ccgame.game.utils.PieceUtils;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

/**
 * Chinese Chess Game.
 *
 * @author Hop Nguyen
 */
@Data
@Builder
public class PlayingGame {

    private static final Logger logger = LoggerFactory.getLogger(PlayingGame.class);

    @NonNull
    private Player redPlayer;

    @NonNull
    private Player blackPlayer;

    private Board board;

    private boolean isRedTurn;

    private boolean isOver;

    private boolean isRedWin;

    private boolean isGeneralChecking;

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
        isGeneralChecking = false;
        moves = new Stack<>();
    }

    /**
     * End this game.
     *
     * @param isRedWin {@code true}  if red player is win and vice versa.
     */
    public void endGame(boolean isRedWin) {
        this.setOver(true);
        this.setRedWin(isRedWin);
    }

    public void doMove(Player player, String move) {
        if (!MoveUtils.isValidMove(move)) {
            logger.error("Invalid move format!");
            throw new InvalidMoveException("Invalid move format!");
        } else if (player.isRed() != isRedTurn) {
            logger.error("Invalid turn!");
            throw new InvalidMoveException("Invalid turn");
        }

        Position from = Position.getPositionFromString(move.substring(0, 2));
        Position to = Position.getPositionFromString(move.substring(3));

        Piece current = board.getPieceByPosition(from);

        if (current == null || !current.isValidMove(board, from, to)) {
            logger.error("Invalid move!");
            throw new InvalidMoveException("Invalid move!");
        }

        Piece[][] pieces = board.getPieces();

        // Do move
        pieces[from.getX()][from.getY()] = null;
        pieces[to.getX()][to.getY()] = current;

        isRedTurn = !isRedTurn;
        moves.push(move);

        // Check general checking
        Board clonedBoard = this.board.clone();
        Position redGeneralPosition = PieceUtils.findMyGeneralPosition(board, true);
        Position blackGeneralPosition = PieceUtils.findMyGeneralPosition(board, false);
        isGeneralChecking = GameUtils.isAbleDead(clonedBoard, redGeneralPosition) || GameUtils
                .isAbleDead(clonedBoard, blackGeneralPosition);

        // Check game isOver
        boolean mockIsOver = true;

        for (int i = 0; i < Board.ROW; i++) {
            for (int j = 0; j < Board.COLUMN; j++) {
                Piece piece = clonedBoard.getPieces()[i][j];
                if (piece != null && piece.isRed() == isRedTurn) {
                    if (MoveUtils.isHaveValidMove(clonedBoard, new Position(i, j))) {
                        mockIsOver = false;
                        break;
                    }
                }
            }
            if (!mockIsOver)
                break;
        }

        if (mockIsOver) {
            logger.info("Game is over! Need to send notification to user!");
            this.endGame(!isRedTurn);
        }
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
