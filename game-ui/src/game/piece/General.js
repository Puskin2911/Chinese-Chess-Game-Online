import Piece from "./Piece";

export default class General extends Piece {
    constructor(shortName, isRed) {
        super(shortName, isRed);
    }

    isValidMove(board, from, to) {
        return super.isValidMove(board, from, to);
    }

}