import Piece from "./Piece";
import pieceUtils from "./PieceUtils";

export default class Chariot extends Piece {
    constructor(shortName, isRed) {
        super(shortName, isRed);
    }

    isValidMove(board, from, to) {
        const xFrom = from.x + "";
        const yFrom = from.y + "";
        const xTo = to.x + "";
        const yTo = to.y + "";

        const pieceAtTo = board[xTo][yTo];

        if (xFrom === xTo && pieceUtils.getPieceBetweenHorizontal(board, xFrom, yFrom, yTo) === 0) {
            if (pieceAtTo == null) return true;
            return pieceAtTo.isRed !== this.isRed;
        }
        if (yFrom === yTo && pieceUtils.getPieceBetweenVertical(board, xFrom, xTo, yFrom) === 0) {
            if (pieceAtTo == null) return true;
            return pieceAtTo.isRed !== this.isRed;
        }

        return false;
    }

}