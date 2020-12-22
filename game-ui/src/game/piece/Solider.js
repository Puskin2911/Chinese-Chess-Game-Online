import Piece from "./Piece";

export default class Solider extends Piece {
    constructor(shortName, isRed) {
        super(shortName, isRed);
    }

    isValidMove(board, from, to) {
        const xFrom = from.x;
        const yFrom = from.y;
        const xTo = to.x;
        const yTo = to.y;

        if (this.isRed) {
            // In home (not pass river)
            if (xFrom >= 5) {
                return (yFrom == yTo && xFrom - xTo === 1);
            }
            // Pass river
            else {
                return (yFrom == yTo && xFrom - xTo === 1) || (xFrom == xTo && Math.abs(yFrom - yTo) === 1);
            }
        } else {
            // In home
            if (xFrom <= 4) {
                return (yFrom == yTo && xFrom - xTo === -1);
            }
            // Not pass river
            else {
                return (yFrom == yTo && xFrom - xTo === -1) || (xFrom === xTo && Math.abs(yFrom - yTo) === 1);
            }
        }
    }

}