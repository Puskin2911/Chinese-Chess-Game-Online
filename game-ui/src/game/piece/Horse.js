import Piece from "./Piece";

export default class Horse extends Piece {
    constructor(shortName, isRed) {
        super(shortName, isRed);
    }

    isValidMove(board, from, to) {
        const xFrom = from.x + "";
        const yFrom = from.y + "";
        const xTo = to.x + "";
        const yTo = to.y + "";

        const pieceAtTo = board[xTo][yTo];

        if (Math.abs(xFrom - xTo) === 1 && yFrom - yTo === 2 && board[xFrom][yFrom - 1] == null) {
            if (pieceAtTo == null) return true;
            return pieceAtTo.isRed !== this.isRed;
        }

        if (Math.abs(xFrom - xTo) === 1 && yFrom - yTo === -2 && board[xFrom][yFrom - 1 + 2] == null) {
            if (pieceAtTo == null) return true;
            return pieceAtTo.isRed !== this.isRed;
        }

        if (xFrom - xTo === 2 && Math.abs(yFrom - yTo) === 1 && board[xFrom - 1][yFrom] == null) {
            if (pieceAtTo == null) return true;
            return pieceAtTo.isRed !== this.isRed;
        }

        if (xFrom - xTo === -2 && Math.abs(yFrom - yTo) === 1 && board[xFrom - 1 + 2][yFrom] == null) {
            if (pieceAtTo == null) return true;
            return pieceAtTo.isRed !== this.isRed;
        }

        return false;
    }

}