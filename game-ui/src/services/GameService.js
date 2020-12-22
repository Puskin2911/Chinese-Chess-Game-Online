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

    const from = {
        x: pieceString.charAt(0),
        y: pieceString.charAt(1)
    }

    for (let i = 0; i < Board.ROW; i++) {
        for (let j = 0; j < Board.COLUMN; j++) {
            const to = {
                x: i + "",
                y: j + ""
            }
            if (pieceObject.isValidMove(pieces, from, to)) {
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

const resolvePosition = (position, isRedPlayer) => {
    if (isRedPlayer) return position;

    const newX = Board.ROW - 1 - position.centerX;
    const newY = Board.COLUMN - 1 - position.centerY;

    return {
        centerX: newX,
        centerY: newY
    }
}

const isValidMove = (boardStatus, movingPiece, to) => {
    const from = {
        x: movingPiece.charAt(0),
        y: movingPiece.charAt(1)
    }

    console.log(JSON.stringify(from));
    console.log(JSON.stringify(to));

    const pieceObject = Board.convertPieceStringToObject(movingPiece);

    return pieceObject.isValidMove(Board.convertToMatrix(boardStatus), from, to);
}

const gameService = {
    resolveBoardStatus,
    resolveMove,
    resolvePosition,
    isMyPiece,
    getAvailableMovePosition,
    isValidMove
}

export default gameService;