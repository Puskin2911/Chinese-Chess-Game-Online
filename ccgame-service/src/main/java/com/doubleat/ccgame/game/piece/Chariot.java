package com.doubleat.ccgame.game.picece;

import com.doubleat.ccgame.game.logic.CCBoard;
import com.doubleat.ccgame.game.logic.MoveUtils;
import com.doubleat.ccgame.game.logic.Position;

public class Chariot extends Piece {
    public Chariot(String shortName) {
        super(shortName);
    }

    @Override
    public boolean isValidMove(CCBoard board, Position from, Position to) {
        return MoveUtils.canMoveAhead(board, from, to)
                || MoveUtils.canMoveBack(board, from, to)
                || MoveUtils.canMoveSide(board, from, to);
    }
}
