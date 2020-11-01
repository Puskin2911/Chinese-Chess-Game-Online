package com.doubleat.ccgame.game.utils;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;
import com.doubleat.ccgame.game.piece.Piece;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public final class MoveUtils {

    private static Piece current;

    private MoveUtils() {
    }

    private static void init(Board board, Position from, Position to) {
        Piece[][] pieces = board.getPieces();
        current = pieces[from.getX()][from.getY()];
    }

    public static boolean isStraightMove(Board board, Position from, Position to) {
        init(board, from, to);
        if (current == null) {
            return false;
        }

        return from.getY() == to.getY() && from.getX() != to.getX();
    }

    public static boolean isSideMove(Board board, Position from, Position to) {
        init(board, from, to);
        if (current == null) {
            return false;
        }

        return from.getX() == to.getX() && from.getY() != to.getY();
    }

    public static boolean isDiagonalMove(Board board, Position from, Position to) {
        init(board, from, to);
        if (current == null) {
            return false;
        }

        int xRange = Math.abs(from.getX() - to.getX());
        int yRange = Math.abs(from.getY() - to.getY());

        return (xRange == yRange && xRange != 0);
    }

    /**
     * Check if move is valid.
     *
     * @param move move need to check.
     * @return {@code true} if the move is valid, other wise return {@code false}.
     */
    public static boolean isValidMove(String move) {
        if (move != null && move.length() == 5) {
            Pattern pattern = Pattern.compile("[0-9][0-8]_[0-9][0-8]");

            return pattern.matcher(move).matches();
        }

        return false;
    }

}