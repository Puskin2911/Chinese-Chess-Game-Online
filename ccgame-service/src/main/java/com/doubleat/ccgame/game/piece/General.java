package com.doubleat.ccgame.game.piece;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;
import com.doubleat.ccgame.game.utils.PieceUtils;

public class General extends Piece {
    public General(String shortName) {
        super(shortName);
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        int xFrom = from.getX();
        int yFrom = from.getY();
        int xTo = to.getX();
        int yTo = to.getY();

        Piece pieceAtTo = board.getPieces()[xTo][yTo];
        if (yFrom >= 3 && yFrom <= 5 && yTo >= 3 && yTo <= 5) {
            if (this.isRed && xFrom >= 7 && xFrom <= 9 && xTo >= 7 && xTo <= 9) {
                if (this.checkMove(xFrom, xTo, yFrom, yTo, pieceAtTo))
                    return true;
            }
            if (!this.isRed && xFrom >= 0 && xFrom <= 2 && xTo <= 2) {
                if (this.checkMove(xFrom, xTo, yFrom, yTo, pieceAtTo))
                    return true;
            }
        }
        // King check king
        if (yFrom == yTo && xFrom != xTo && pieceAtTo != null && pieceAtTo.getShortName().equals("ge")
                && PieceUtils.getPieceBetweenVertical(board, xFrom, xTo, yTo) == 0) {
            return true;
        }

        return false;
    }

    public boolean checkMove(int xFrom, int xTo, int yFrom, int yTo, Piece pieceAtTo) {
        // Go to horizontal
        if (xFrom == xTo && Math.abs(yFrom - yTo) == 1) {
            // Just move
            if (pieceAtTo == null)
                return true;
            // Fight (eat piece).
            return pieceAtTo.isRed != this.isRed;
        }
        // Go to vertical
        else if (yFrom == yTo && Math.abs(xFrom - xTo) == 1) {
            // Just move
            if (pieceAtTo == null)
                return true;
            // Fight (eat piece).
            return pieceAtTo.isRed != this.isRed;
        }
        return false;
    }

}
