import Piece from "./Piece";

export default class Cannon extends Piece {
    constructor(shortName, isRed) {
        super(shortName, isRed);
    }

    isValidMove(board, from, to) {
        return true;
    }

}