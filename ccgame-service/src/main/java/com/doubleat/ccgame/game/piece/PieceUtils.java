package com.doubleat.ccgame.game.piece;

import com.doubleat.ccgame.game.Board;

public class PieceUtils {
    public static int getPieceBetweenVertical(Board board, int xStart, int xEnd, int y){
        int numberOfPiece = 0;
        if (xStart > xEnd) {
            int temp = xStart;
            xStart = xEnd;
            xEnd = temp;
        }
        for (int x = xStart - 1 + 2; x < xEnd; x++) {
            if (board.getPieces()[x][y] != null) numberOfPiece++;
        }
        return numberOfPiece;
    }

    public static int getPieceBetweenHorizontal(Board board,int x, int yStart, int yEnd){
        if (yStart > yEnd) {
            int temp = yStart;
            yStart = yEnd;
            yEnd = temp;
        }

        int numberOfPiece = 0;

        for (int y = yStart - 1 + 2; y < yEnd; y++) {
            if (board.getPieces()[x][y] != null) numberOfPiece++;
        }

        return numberOfPiece;
    }
}
