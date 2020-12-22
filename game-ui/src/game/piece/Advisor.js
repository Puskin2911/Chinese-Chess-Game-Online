import Piece from "./Piece";

export default class Advisor extends Piece {
    constructor(shortName, isRed) {
        super(shortName, isRed);
    }

    isValidMove(board, from, to) {
        const xFrom = from.x + "";
        const yFrom = from.y + "";
        const xTo = to.x + "";
        const yTo = to.y + "";

        const pieceAtTo = board[xTo][yTo];

        if (yFrom >= 3 && yFrom <= 5 && yTo >= 3 && yTo <= 5) {
            if (Math.abs(xFrom - xTo) === 1 && Math.abs(yFrom - yTo) === 1) {
                if (this.isRed && xFrom >= 7 && xFrom <= 9 && xTo >= 7 && xTo <= 9) {
                    // Just move
                    if (pieceAtTo == null) return true;
                    // Fight (eat piece).
                    return pieceAtTo.isRed !== this.isRed;
                }
                if (!this.isRed && xFrom >= 0 && xFrom <= 2 && xTo >= 0 && xTo <= 2) {
                    // Just move
                    if (pieceAtTo == null) return true;
                    // Fight (eat piece).
                    return pieceAtTo.isRed !== this.isRed;
                }
            }
        }

        return false;
    }

}