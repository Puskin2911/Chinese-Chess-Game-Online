package com.doubleat.ccgame.game.piece;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;

public class Advisor extends Piece {
    public Advisor(String shortName) {
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
            if (Math.abs(xFrom - xTo) == 1 && Math.abs(yFrom - yTo) == 1) {
                if (this.isRed && xFrom >= 7 && xFrom <= 9 && xTo >= 7 && xTo <= 9) {
                    if (pieceAtTo == null) {
                        return true;
                    }
                    return !pieceAtTo.isRed;
                }
                if (!this.isRed && xFrom >= 0 && xFrom <= 2 && xTo <= 2) {
                    // Just move
                    if (pieceAtTo == null)
                        return true;
                    // Fight (eat piece).
                    return pieceAtTo.isRed;
                }
            }
        }
        return false;
    }

}
