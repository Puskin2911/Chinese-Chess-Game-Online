package com.doubleat.ccgame.game.utils;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;
import com.doubleat.ccgame.game.exception.PieceNotFoundException;
import com.doubleat.ccgame.game.piece.Piece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MoveUtils {

    private static final Logger logger = LoggerFactory.getLogger(MoveUtils.class);

    private MoveUtils() {
    }

    private static void preCheck(Board board, Position from, Position to) {
        Piece[][] pieces = board.getPieces();
        Piece current = pieces[from.getX()][from.getY()];
        if (current == null) {
            logger.error("Piece can not be NULL");
            throw new PieceNotFoundException();
        }
    }

    public static boolean isStraightMove(Board board, Position from, Position to) {
        preCheck(board, from, to);

        return from.getY() == to.getY() && from.getX() != to.getX();
    }

    public static boolean isSideMove(Board board, Position from, Position to) {
        preCheck(board, from, to);

        return from.getX() == to.getX() && from.getY() != to.getY();
    }

    public static boolean isDiagonalMove(Board board, Position from, Position to) {
        preCheck(board, from, to);

        int xRange = Math.abs(from.getX() - to.getX());
        int yRange = Math.abs(from.getY() - to.getY());

        return (xRange == yRange && xRange != 0);
    }

}