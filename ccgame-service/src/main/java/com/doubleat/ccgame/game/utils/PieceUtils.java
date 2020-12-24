package com.doubleat.ccgame.game.utils;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.piece.*;

public final class PieceUtils {
    private PieceUtils() {
    }

    public static boolean isGeneral(Piece piece) {
        return piece.getShortName().equals("ge");
    }

    public static Piece getInstancePieceFromShortName(String shortName) {
        switch (shortName) {
        case "ch": {
            return new Chariot(shortName);
        }
        case "ho": {
            return new Horse(shortName);
        }
        case "el": {
            return new Elephant(shortName);
        }
        case "ad": {
            return new Advisor(shortName);
        }
        case "ge": {
            return new General(shortName);
        }
        case "ca": {
            return new Cannon(shortName);
        }
        case "so": {
            return new Solider(shortName);
        }
        default:
            throw new IllegalArgumentException("Unexpected value: " + shortName);
        }
    }
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
