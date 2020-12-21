import Board from "../game/Board";

const resolveBoardStatus = (boardStatus, isRedPlayer) => {
    if (isRedPlayer) return boardStatus;

    console.log("input resolveBoardStatus: " + boardStatus);
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

    console.log("output resolveBoardStatus: " + res);

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

const gameService = {
    resolveBoardStatus,
    resolveMove
}

export default gameService;