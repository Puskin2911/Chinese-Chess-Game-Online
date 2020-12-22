import Board from "../game/Board";

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
    isMyPiece,
    getAvailableMovePosition,
    isValidMove
}

export default gameService;