const getPieceBetweenVertical = (pieces, xStart, xEnd, y) => {
    // Verify start < end
    if (xStart > xEnd) {
        const temp = xStart;
        xStart = xEnd;
        xEnd = temp;
    }

    let numberOfPiece = 0;

    for (let x = xStart + 1; x < xEnd; x++) {
        if (pieces[x][y] != null) numberOfPiece++;
    }

    return numberOfPiece;
}

const getPieceBetweenHorizontal = (pieces, x, yStart, yEnd) => {
    // Verify start < end
    if (yStart > yEnd) {
        const temp = yStart;
        yStart = yEnd;
        yEnd = temp;
    }

    let numberOfPiece = 0;

    for (let y = yStart + 1; y < yEnd; y++) {
        if (pieces[x][y] != null) numberOfPiece++;
    }

    return numberOfPiece;
}


const pieceUtils = {
    getPieceBetweenVertical,
    getPieceBetweenHorizontal
}

export default pieceUtils;