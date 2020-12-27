package com.doubleat.ccgame.game.piece;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;
import com.doubleat.ccgame.game.utils.PieceUtils;

public class Chariot extends Piece {
    public Chariot(String shortName) {
        super(shortName);
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        int xFrom = from.getX();
        int yFrom = from.getY();
        int xTo = to.getX();
        int yTo = to.getY();

        Piece pieceAtTo = board.getPieces()[xTo][yTo];

        if (xFrom == xTo && PieceUtils.getPieceBetweenHorizontal(board, xFrom, yFrom, yTo) == 0) {
            if (pieceAtTo == null)
                return true;
            return pieceAtTo.isRed != this.isRed;
        }
        if (yFrom == yTo && PieceUtils.getPieceBetweenVertical(board, xFrom, xTo, yFrom) == 0) {
            if (pieceAtTo == null)
                return true;
            return pieceAtTo.isRed != this.isRed;
        }
        return false;
    }

}
