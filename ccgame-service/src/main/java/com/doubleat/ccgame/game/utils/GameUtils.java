package com.doubleat.ccgame.game.utils;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;
import com.doubleat.ccgame.game.piece.Piece;

public final class GameUtils {

    public GameUtils() {
    }

    public static boolean isAbleDead(Board board, Position currentPosition) {
        for (int i = 0; i < Board.ROW; i++) {
            for (int j = 0; j < Board.COLUMN; j++) {
                Piece piece = board.getPieces()[i][j];
                if (piece != null && piece.isValidMove(board, new Position(i, j), currentPosition))
                    return true;
            }
        }

        return false;
    }

}
