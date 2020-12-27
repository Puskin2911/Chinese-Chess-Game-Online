package com.doubleat.ccgame.game.piece;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;
import com.doubleat.ccgame.game.utils.PieceUtils;

public class Cannon extends Piece {
    public Cannon(String shortName) {
        super(shortName);
    }

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        int xFrom = from.getX();
        int yFrom = from.getY();
        int xTo = to.getX();
        int yTo = to.getY();

        Piece pieceAtTo = board.getPieces()[xTo][yTo];

        if (xFrom == xTo) {
            if (PieceUtils.getPieceBetweenHorizontal(board, xFrom, yFrom, yTo) == 0 && pieceAtTo == null)
                return true;
            if (PieceUtils.getPieceBetweenHorizontal(board, xFrom, yFrom, yTo) == 1 && pieceAtTo != null)
                return pieceAtTo.isRed != this.isRed;

        }
        if (yFrom == yTo) {
            if (PieceUtils.getPieceBetweenVertical(board, xFrom, xTo, yFrom) == 0 && pieceAtTo == null)
                return true;
            if (PieceUtils.getPieceBetweenVertical(board, xFrom, xTo, yFrom) == 1 && pieceAtTo != null)
                return pieceAtTo.isRed != this.isRed;
        }
        return false;
    }

}
