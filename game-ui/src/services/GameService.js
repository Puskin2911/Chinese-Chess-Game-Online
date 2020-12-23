import Board from "../game/Board";

const resolveBoardStatus = (boardStatus, isRedPlayer) => {
    if (isRedPlayer) return boardStatus;

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

const getAvailableMovePosition = (pieceString, boardStatus, isRedPlayer) => {
    const availableMovePosition = [];

    let resolveBoardStatus = boardStatus;
    let resolvePieceString = pieceString;

    if (!isRedPlayer) {
        boardStatus = boardStatus.replaceAll("r", "t");
        boardStatus = boardStatus.replaceAll("b", "r");
        boardStatus = boardStatus.replaceAll("t", "b");
        pieceString = pieceString.replaceAll("b", "r");
    }
    const pieces = Board.convertToMatrix(boardStatus);
    const pieceObject = Board.convertPieceStringToObject(pieceString);

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
            if (resolvePieceObject.isValidMove(resolvePieces, from, to)) {
                let mockBoardStatus = doMove(boardStatus, from, to);
                mockBoardStatus = gameService.resolveBoardStatus(mockBoardStatus, false);
                const myGeneral = findMyGeneral(mockBoardStatus, isRedPlayer);

                for (let k = 0; k < Board.ROW; k++) {
                    for (let h = 0; h < Board.COLUMN; h++) {
                        const mockFrom = {
                            x: k + "",
                            y: h + ""
                        }
                        if (mockPieces[k][h] != null && mockPieces[k][h].isValidMove(mockPieces, mockFrom, mockTo)) {
                            isAvailable = false;
                            break;
                        }
                    }
                    if (isAvailable === false) break;
                }

                if (isAvailable) {
                    const position = {
                        centerX: i,
                        centerY: j
                    }
                    availableMovePosition.push(position);
                }
            }
        }
    }

    return availableMovePosition;
}

const findMyGeneral = (boardStatus, isRedPlayer) => {
    const color = isRedPlayer ? "r" : "b";
    const index = boardStatus.indexOf(color + "ge");

    return boardStatus.slice(index - 2, index) + color + "ge";
}

const isValidMove = (boardStatus, movingPiece, to, isRedPlayer) => {
    const from = {
        x: movingPiece.charAt(0),
        y: movingPiece.charAt(1)
    }

    if (!isRedPlayer) {
        boardStatus = boardStatus.replaceAll("r", "t");
        boardStatus = boardStatus.replaceAll("b", "r");
        boardStatus = boardStatus.replaceAll("t", "b");
        movingPiece = movingPiece.replaceAll("b", "r");
    }

    const pieceObject = Board.convertPieceStringToObject(movingPiece);

    return pieceObject.isValidMove(Board.convertToMatrix(boardStatus), from, to);
}

const gameService = {
    isMyPiece,
    resolveMove,
    getAvailableMovePosition,
    resolveBoardStatus,
    isValidMove
}

export default gameService;