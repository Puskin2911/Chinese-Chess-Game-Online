import React from "react";
import Position from "../utils/Position";
import useDidUpdateEffect from "../common/CustomizeHook";
import {BOARD_DEFAULT_STATUS, BOARD_HEIGHT_SIZE, BOARD_WIDTH_SIZE, CELL_SIZE} from "../constants/BoardConstants";
import canvasService from "../services/CanvasService";

function Board(props) {

    const room = props.room;
    const roomId = room.id;
    const user = props.user;
    const isGameStarted = props.isGameStarted;
    const setGameStarted = props.setGameStarted;
    const stompClient = props.stompClient;

    const canvasRef = React.useRef(null);

    const [boardStatus, setBoardStatus] = React.useState(BOARD_DEFAULT_STATUS);
    const [movingPiece, setMovingPiece] = React.useState("00000");
    const [centerX, setCenterX] = React.useState(0);
    const [centerY, setCenterY] = React.useState(0);

    const drawBoard = () => {
        const canvas = canvasRef.current;
        const ctx = canvas.getContext('2d');
        canvasService.clearBoard(canvas);
        canvasService.drawBlankBoard(ctx);
        canvasService.drawPieces(ctx, boardStatus);
    };


    function handleMove(event) {
        const canvas = canvasRef.current;
        const rect = canvas.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;

        console.log('x: ' + x + '\ny: ' + y);

        const position = new Position(x, y);
        const xy = position.getXY();
        console.log(xy);
        if (xy !== '-1') {
            let index = boardStatus.indexOf(xy);
            let color = boardStatus.charAt(index + 2);
            const piece = boardStatus.slice(index, index + 5);

            setCenterX(xy.slice(1) * (CELL_SIZE + 1) + 1);
            setCenterY(xy.slice(0, 1) * (CELL_SIZE + 1) + 1);

            if (movingPiece === '00000') {
                if (color !== '0') {
                    console.log("Picked " + piece);
                    setMovingPiece(piece);
                    console.log(centerX, centerY);
                }
            } else {
                let newBoardStatus;
                newBoardStatus = boardStatus.replaceAll(movingPiece, movingPiece.substring(0, 2) + '000');
                newBoardStatus = newBoardStatus.replaceAll(piece, piece.substring(0, 2) + movingPiece.substring(2));

                console.log(canvas.clientWidth, canvas.clientHeight);
                console.log(centerX, centerY);

                setMovingPiece('00000');
                setBoardStatus(newBoardStatus);
            }
        }
    }

    React.useEffect(() => {
        if (isGameStarted) {
            drawBoard();
        }
    }, [drawBoard]);

    useDidUpdateEffect(() => {
        canvasService.drawMovingPiece(canvasRef.current.getContext("2d"), centerX, centerY);
    }, [centerX, centerY]);

    React.useEffect(() => {
        stompClient.subscribe("/move/room/" + roomId, (payload) => {
            alert("got message with body " + payload.body);
            console.log("receive payload from Board: " + JSON.parse(payload.body));
        });
    }, []);

    console.log("Before rendering in Board...");
    return (
        <div className="col-6">
            <canvas ref={canvasRef}
                    className="border border-success"
                    width={BOARD_WIDTH_SIZE}
                    height={BOARD_HEIGHT_SIZE}
                    onClick={handleMove}
            />
        </div>
    )
}

export default Board;