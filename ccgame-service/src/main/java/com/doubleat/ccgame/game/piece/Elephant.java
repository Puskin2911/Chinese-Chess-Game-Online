package com.doubleat.ccgame.game.piece;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;

public class Elephant extends Piece {
    public Elephant(String shortName) {
        super(shortName);
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        int xFrom = from.getX();
        int yFrom = from.getY();
        int xTo = to.getX();
        int yTo = to.getY();

        Piece pieceAtTo = board.getPieces()[xTo][yTo];

        if (Math.abs(xFrom - xTo) == 2 && Math.abs(yFrom - yTo) == 2
                && board.getPieces()[(xFrom + xTo) / 2][(yFrom + yTo) / 2] == null) {
            if (this.isRed && xFrom >= 5 && xTo >= 5 && xFrom <= 9 && xTo <= 9) {
                if (pieceAtTo == null)
                    return true;
                return !pieceAtTo.isRed;
            }

            if (!this.isRed && xFrom >= 0 && xFrom <= 4 && xTo <= 4) {
                if (pieceAtTo == null)
                    return true;
                return pieceAtTo.isRed;
            }
        }
        return false;
    }

}
