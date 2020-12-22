import Piece from "./Piece";

export default class Soldier extends Piece {
    constructor(shortName, isRed) {
        super(shortName, isRed);
    }

    isValidMove(board, from, to) {
        const xFrom = from.x + "";
        const yFrom = from.y + "";
        const xTo = to.x + "";
        const yTo = to.y + "";

        const pieceAtTo = board[xTo][yTo];

        if (this.isRed) {
            // Go straight
            if (yFrom === yTo && xFrom - xTo === 1) {
                // Just move
                if (pieceAtTo == null) return true;
                // Fight (eat piece).
                return pieceAtTo.isRed !== this.isRed;
            }

            // Pass river and go side
            return xFrom === xTo && Math.abs(yFrom - yTo) === 1 && xFrom <= 4;
        } else {
            // Go straight
            if (yFrom === yTo && xFrom - xTo === -1) {
                // Just move
                if (pieceAtTo == null) return true;
                // Fight (eat piece).
                return pieceAtTo.isRed !== this.isRed;
            }

            // Pass river and go side
            return xFrom === xTo && Math.abs(yFrom - yTo) === 1 && xFrom >= 5;
        }
    }

}