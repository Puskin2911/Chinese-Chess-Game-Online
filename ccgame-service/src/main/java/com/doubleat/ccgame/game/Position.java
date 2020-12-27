package com.doubleat.ccgame.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Position {
    private int x;
    private int y;

    public static Position getPositionFromString(String text) {
        int x = Character.getNumericValue(text.charAt(0));
        int y = Character.getNumericValue(text.charAt(1));

        return new Position(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
