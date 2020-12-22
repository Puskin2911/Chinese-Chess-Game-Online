import Piece from "./Piece";

export default class Elephant extends Piece {
    constructor(shortName, isRed) {
        super(shortName, isRed);
    }

    isValidMove(board, from, to) {
        const xFrom = from.x + "";
        const yFrom = from.y + "";
        const xTo = to.x + "";
        const yTo = to.y + "";

        const pieceAtTo = board[xTo][yTo];
        console.log(xFrom * 1 + xTo * 1);

        if (Math.abs(xFrom - xTo) === 2 && Math.abs(yFrom - yTo) === 2
            && board[(xFrom * 1 + xTo * 1) / 2][(yFrom * 1 + yTo * 1) / 2] == null) {
            if (this.isRed && xFrom >= 5 && xTo >= 5 && xFrom <= 9 && xTo <= 9) {
                if (pieceAtTo == null) return true;
                return pieceAtTo.isRed !== this.isRed;
            }

            if (!this.isRed && xFrom >= 0 && xTo >= 0 && xFrom <= 4 && xTo <= 4) {
                if (pieceAtTo == null) return true;
                return pieceAtTo.isRed !== this.isRed;
            }
        }

        return false;
    }

}