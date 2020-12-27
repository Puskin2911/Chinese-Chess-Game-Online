package com.doubleat.ccgame.game.piece;

import com.doubleat.ccgame.game.Board;
import com.doubleat.ccgame.game.Position;
import com.doubleat.ccgame.game.utils.PieceUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public abstract class Piece {

    protected final String shortName;

    protected boolean isRed;

    public boolean isValidMove(Board board, Position from, Position to) {
        return true;
    }

    @Override
    public Piece clone() {
        Piece clonedPiece = PieceUtils.getPieceInstanceFromShortName(shortName);
        clonedPiece.setRed(isRed);
        return clonedPiece;
    }

}
