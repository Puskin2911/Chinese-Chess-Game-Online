package com.doubleat.ccgame.game.utils;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;
import com.doubleat.ccgame.game.piece.Piece;

import java.util.regex.Pattern;

public final class MoveUtils {

    private MoveUtils() {
    }

    /**
     * Check if move is valid.
     *
     * @param move move need to check.
     * @return {@code true} if the move is valid, other wise return {@code false}.
     */
    public static boolean isValidMove(String move) {
        if (move != null && move.length() == 5) {
            Pattern pattern = Pattern.compile("[0-9][0-8]_[0-9][0-8]");

            return pattern.matcher(move).matches();
        }

        return false;
    }

    public static boolean isHaveValidMove(Board board, Position currentPosition) {
        Piece currentPiece = board.getPieceByPosition(currentPosition);

        for (int i = 0; i < Board.ROW; i++) {
            for (int j = 0; j < Board.COLUMN; j++) {
                Position toPosition = new Position(i, j);
                if (currentPiece.isValidMove(board, currentPosition, toPosition)) {
                    Board mockBoard = board.clone();
                    Piece[][] mockPieces = mockBoard.getPieces();
                    mockPieces[i][j] = currentPiece.clone();
                    mockPieces[currentPosition.getX()][currentPosition.getY()] = null;

                    Position myGeneralPosition = PieceUtils.findMyGeneralPosition(mockBoard, currentPiece.isRed());
                    if (!GameUtils.isAbleDead(mockBoard, myGeneralPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}