package com.doubleat.ccgame.game.picece;

import com.doubleat.ccgame.game.logic.CCBoard;
import com.doubleat.ccgame.game.logic.Position;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public abstract class Piece {
    protected final String shortName;
    protected boolean isRed;

    public boolean isValidMove(CCBoard board, Position from, Position to) {
        return false;
    }
}
