package com.doubleat.ccgame.game.piece;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;

public class Solider extends Piece {
    public Solider(String shortName) {
        super(shortName);
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        int xFrom = from.getX();
        int yFrom = from.getY();
        int xTo = to.getX();
        int yTo = to.getY();

        Piece pieceAtTo = board.getPieces()[xTo][yTo];
        if (this.isRed) {
            // Go straight
            if (yFrom == yTo && xFrom - xTo == 1) {
                // Just move
                if (pieceAtTo == null)
                    return true;
                // Fight (eat piece).
                return !pieceAtTo.isRed;
            }
            // Pass river and go side
            return xFrom == xTo && Math.abs(yFrom - yTo) == 1 && xFrom <= 4;
        } else {
            // Go straight
            if (yFrom == yTo && xFrom - xTo == -1) {
                // Just move
                if (pieceAtTo == null)
                    return true;
                // Fight (eat piece).
                return pieceAtTo.isRed;
            }
            // Pass river and go side
            return xFrom == xTo && Math.abs(yFrom - yTo) == 1 && xFrom >= 5;
        }
    }

}
