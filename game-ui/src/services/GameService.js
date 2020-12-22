import Board from "../game/Board";

const resolveBoardStatus = (boardStatus, isRedPlayer) => {
    if (isRedPlayer) return boardStatus;

    // console.log("input resolveBoardStatus: " + boardStatus);
    let res = "";
    const pieces = boardStatus.split("_");

    const length = pieces.length;
    for (let i = 0; i < length; i++) {
        const piece = pieces[i];
        const x = piece.charAt(0);
        const y = piece.charAt(1);
        const color = piece.charAt(2);
        const shortName = piece.slice(3, 5);

        res += "" + (Board.ROW - 1 - x) + (Board.COLUMN - 1 - y) + color + shortName;

        if (i !== length - 1) res += "_";
    }

    // console.log("output resolveBoardStatus: " + res);

    return res;
}

const resolveMove = (move, isRedPlayer) => {
    if (isRedPlayer) return move;

    const xFrom = move.charAt(0);
    const yForm = move.charAt(1);
    const xTo = move.charAt(3);
    const yTo = move.charAt(4);

    return "" + (Board.ROW - 1 - xFrom) + (Board.COLUMN - 1 - yForm)
        + "_" + (Board.ROW - 1 - xTo) + (Board.COLUMN - 1 - yTo);
}

const isMyPiece = (color, isRedPlayer) => {
    if (isRedPlayer) {
        if (color === 'r') return true;
    } else {
        if (color === 'b') return true;
    }

    return false;
}

const getAvailableMovePosition = (pieceString, boardStatus) => {
    const availableMovePosition = [];
    const pieceObject = Board.convertPieceStringToObject(pieceString);

    console.log("Piece Object:" + JSON.stringify(pieceObject));

    const pieces = Board.convertToMatrix(boardStatus);

    for (let i = 0; i < Board.ROW; i++) {
        for (let j = 0; j < Board.COLUMN; j++) {
            if (pieceObject.isValidMove(pieces, "", "")) {
                const position = {
                    centerX: i,
                    centerY: j
                }
                availableMovePosition.push(position);
                console.log("have new available position!");
            }
        }
    }

    console.log("Available before return: " + JSON.stringify(availableMovePosition));
    return availableMovePosition;
}

const gameService = {
    resolveBoardStatus,
    resolveMove,
    isMyPiece,
    getAvailableMovePosition
}

export default gameService;