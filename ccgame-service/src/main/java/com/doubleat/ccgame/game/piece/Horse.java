package com.doubleat.ccgame.game.piece;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;

public class Horse extends Piece {
    public Horse(String shortName) {
        super(shortName);
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        int xFrom = from.getX();
        int yFrom = from.getY();
        int xTo = to.getX();
        int yTo = to.getY();

        Piece pieceAtTo = board.getPieces()[xTo][yTo];

        if (Math.abs(xFrom - xTo) == 1 && yFrom - yTo == 2 && board.getPieces()[xFrom][yFrom - 1] == null) {
            if (pieceAtTo == null)
                return true;
            return pieceAtTo.isRed != this.isRed;
        }
        if (Math.abs(xFrom - xTo) == 1 && yFrom - yTo == -2 && board.getPieces()[xFrom][yFrom - 1 + 2] == null) {
            if (pieceAtTo == null)
                return true;
            return pieceAtTo.isRed != this.isRed;
        }
        if (xFrom - xTo == 2 && Math.abs(yFrom - yTo) == 1 && board.getPieces()[xFrom - 1][yFrom] == null) {
            if (pieceAtTo == null)
                return true;
            return pieceAtTo.isRed != this.isRed;
        }
        if (xFrom - xTo == -2 && Math.abs(yFrom - yTo) == 1 && board.getPieces()[xFrom - 1 + 2][yFrom] == null) {
            if (pieceAtTo == null)
                return true;
            return pieceAtTo.isRed != this.isRed;
        }
        return false;
    }

}
