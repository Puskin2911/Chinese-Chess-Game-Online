package com.doubleat.ccgame.game.utils;

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

}
