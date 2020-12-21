import Piece from "./Piece";

export default class Advisor extends Piece {
    constructor(shortName, isRed) {
        super(shortName, isRed);
    }

    isValidMove(board, from, to) {
        return true;
    }

}