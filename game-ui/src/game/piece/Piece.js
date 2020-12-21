export default class Piece {
    constructor(shortName, isRed) {
        this.shortName = shortName;
        this.isRed = isRed;
    }

    isValidMove(board, from, to) {
        return true;
    }

}