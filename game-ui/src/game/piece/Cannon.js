import Piece from "./Piece";
import pieceUtils from "./PieceUtils";

export default class Cannon extends Piece {
    constructor(shortName, isRed) {
        super(shortName, isRed);
    }

    isValidMove(board, from, to) {
        const xFrom = from.x + "";
        const yFrom = from.y + "";
        const xTo = to.x + "";
        const yTo = to.y + "";

        const pieceAtTo = board[xTo][yTo];

        if (xFrom === xTo) {
            if (pieceUtils.getPieceBetweenHorizontal(board, xFrom, yFrom, yTo) === 0 && pieceAtTo == null) return true;
            if (pieceUtils.getPieceBetweenHorizontal(board, xFrom, yFrom, yTo) === 1 && pieceAtTo != null)
                return pieceAtTo.isRed !== this.isRed;

        }
        if (yFrom === yTo) {
            if (pieceUtils.getPieceBetweenVertical(board, xFrom, xTo, yFrom) === 0 && pieceAtTo == null) return true;
            if (pieceUtils.getPieceBetweenVertical(board, xFrom, xTo, yFrom) === 1 && pieceAtTo != null)
                return pieceAtTo.isRed !== this.isRed;
        }

        return false;
    }

}