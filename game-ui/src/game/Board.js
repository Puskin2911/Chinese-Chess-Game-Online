import Advisor from "./piece/Advisor";
import Cannon from "./piece/Cannon";
import Chariot from "./piece/Chariot";
import Elephant from "./piece/Elephant";
import General from "./piece/General";
import Horse from "./piece/Horse";
import Soldier from "./piece/Soldier";

export default class Board {
    static COLUMN = 9;
    static ROW = 10;

    constructor() {
        this.pieces = [];
        for (let i = 0; i < Board.ROW; i++) {
            this.pieces.push([]);
        }
    }


    static convertToMatrix(boardStatus) {
        const pieces = [];
        for (let i = 0; i < Board.ROW; i++) {
            pieces.push([]);
        }

        const piecesString = boardStatus.split("_");

        for (let i = 0; i < piecesString.length; i++) {
            const x = piecesString[i].charAt(0);
            const y = piecesString[i].charAt(1);
            const color = piecesString[i].slice(2, 3);

            if (color === '0') {
                pieces[x][y] = null;
            } else {
                const shortName = piecesString[i].slice(3, 5);
                pieces[x][y] = Board.getInstanceFromShortName(shortName, color === "r");
            }
        }

        return pieces;
    }

    static convertToString(pieces) {
        let boardStatus = '';
        for (let i = 0; i < Board.ROW; i++) {
            for (let j = 0; i < Board.COLUMN; j++) {
                const piece = pieces[i][j];
                if (piece != null) {
                    const color = piece.isRed() ? "r" : "b";
                    boardStatus += "" + i + j + color + piece.shortName;
                } else {
                    boardStatus += "" + i + j + "000";
                }

                if (i !== Board.ROW - 1 || j !== Board.COLUMN) {
                    boardStatus += '_';
                }
            }
        }

        return boardStatus;
    }

    static convertPieceStringToObject(piece) {
        const isRed = piece.charAt(2) === "r";
        const shortName = piece.slice(3, 5);

        return this.getInstanceFromShortName(shortName, isRed);
    }

    static getInstanceFromShortName(shortName, isRed) {
        switch (shortName) {
            case "ch": {
                return new Chariot(shortName, isRed);
            }
            case "ho": {
                return new Horse(shortName, isRed);
            }
            case "el": {
                return new Elephant(shortName, isRed);
            }
            case "ad": {
                return new Advisor(shortName, isRed);
            }
            case "ge": {
                return new General(shortName, isRed);
            }
            case "ca": {
                return new Cannon(shortName, isRed);
            }
            case "so": {
                return new Soldier(shortName, isRed);
            }
        }
    }

}