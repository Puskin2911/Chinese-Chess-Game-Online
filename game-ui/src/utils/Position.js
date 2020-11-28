import BoardConstants from "../constants/BoardConstants";

export default class Position {
    constructor(clientX, clientY) {
        this.clientX = clientX;
        this.clientY = clientY;
    }

    getXY() {
        const CELL_SIZE = BoardConstants.CELL_SIZE;
        for (let i = 0; i < 10; i++) {
            for (let j = 0; j < 9; j++) {
                let xCoordinate = CELL_SIZE / 2 + (CELL_SIZE + 1) * j + 1;
                let yCoordinate = CELL_SIZE / 2 + (CELL_SIZE + 1) * i + 1;

                if (this.getDistance(xCoordinate, yCoordinate) <= CELL_SIZE / 2) {
                    return i + '' + j;
                }
            }
        }
        return '-1';
    }

    getDistance(toX, toY) {
        return Math.sqrt(
            Math.pow(this.clientX - toX, 2) + Math.pow(this.clientY - toY, 2)
        );
    }
}