import React from "react";
import imagePieceMap from "../common/ImagePiece";
import Position from "../utils/Position";
import useDidUpdateEffect from "../common/CustomizeHook";
import BoardConstants from "../constants/BoardConstants";
import ApiConstants from "../constants/ApiConstant";
import SockJsClient from "react-stomp";

function Board(props) {

    const room = props.room;
    const roomId = room.id;
    const user = props.user;
    const isGameStarted = props.isGameStarted;
    const setGameStarted = props.setGameStarted;

    const canvasRef = React.useRef(null);
    const wsClientRef = React.useRef(null);

    const [isReady, setReady] = React.useState(false);

    const CELL_SIZE = BoardConstants.CELL_SIZE;

    const [boardStatus, setBoardStatus] = React.useState(BoardConstants.BOARD_DEFAULT_STATUS);
    const [movingPiece, setMovingPiece] = React.useState("00000");
    const [centerX, setCenterX] = React.useState(0);
    const [centerY, setCenterY] = React.useState(0);

    const drawBlankBoard = (ctx) => {
        console.log("start drawBlankBoard....");
        // Horizontal
        for (let i = 0; i < 10; i++) {
            ctx.beginPath();
            ctx.lineWidth = 1;
            ctx.strokeStyle = 'black';
            ctx.moveTo(CELL_SIZE / 2 + 1, CELL_SIZE / 2 + (CELL_SIZE + 1) * i + 1);
            ctx.lineTo(CELL_SIZE * 8.5 + 8 + 1, CELL_SIZE / 2 + (CELL_SIZE + 1) * i + 1);
            ctx.stroke();
        }

        // Vertical
        for (let i = 0; i < 9; i++) {
            ctx.beginPath();
            ctx.lineWidth = 1;
            ctx.strokeStyle = 'black';
            ctx.moveTo(CELL_SIZE / 2 + (CELL_SIZE + 1) * i + 1, CELL_SIZE / 2 + 1);
            ctx.lineTo(CELL_SIZE / 2 + (CELL_SIZE + 1) * i + 1, CELL_SIZE * 9.5 + 10);
            ctx.stroke();
        }

        // River Side
        ctx.clearRect(CELL_SIZE / 2 + 2, CELL_SIZE * 4.5 + 6, CELL_SIZE * 8, CELL_SIZE - 1);

        // General house
        ctx.beginPath();
        // Top
        ctx.moveTo(CELL_SIZE / 2 + (CELL_SIZE + 1) * 3 + 1, CELL_SIZE / 2 + 1);
        ctx.lineTo(CELL_SIZE / 2 + (CELL_SIZE + 1) * 5 + 1, CELL_SIZE / 2 + (CELL_SIZE + 1) * 2 + 1);
        ctx.moveTo(CELL_SIZE / 2 + (CELL_SIZE + 1) * 3 + 1, CELL_SIZE / 2 + (CELL_SIZE + 1) * 2 + 1);
        ctx.lineTo(CELL_SIZE / 2 + (CELL_SIZE + 1) * 5 + 1, CELL_SIZE / 2 + 1);
        // Bot
        ctx.moveTo(CELL_SIZE / 2 + (CELL_SIZE + 1) * 3 + 1, CELL_SIZE / 2 + (CELL_SIZE + 1) * 7 + 1);
        ctx.lineTo(CELL_SIZE / 2 + (CELL_SIZE + 1) * 5 + 1, CELL_SIZE / 2 + (CELL_SIZE + 1) * 9 + 1);
        ctx.moveTo(CELL_SIZE / 2 + (CELL_SIZE + 1) * 3 + 1, CELL_SIZE / 2 + (CELL_SIZE + 1) * 9 + 1);
        ctx.lineTo(CELL_SIZE / 2 + (CELL_SIZE + 1) * 5 + 1, CELL_SIZE / 2 + (CELL_SIZE + 1) * 7 + 1);
        ctx.stroke();
    };

    const drawPieces = (ctx) => {
        const piecesOnBoard = boardStatus.split("_");
        for (let [key, value] of imagePieceMap) {
            for (const piece of piecesOnBoard) {
                if (key === piece.slice(2)) {
                    let x = piece.charAt(0);
                    let y = piece.charAt(1);
                    ctx.drawImage(value, (CELL_SIZE + 1) * y + 1, (CELL_SIZE + 1) * x + 1, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    };

    const drawBoard = () => {
        const canvas = canvasRef.current;
        const ctx = canvas.getContext('2d');
        ctx.clearRect(0, 0, canvas.clientWidth, canvas.clientHeight);
        drawBlankBoard(ctx);
        drawPieces(ctx);
    };

    const drawMovingPiece = (x, y) => {
        const ctx = canvasRef.current.getContext('2d');
        ctx.beginPath();
        ctx.lineWidth = 2;
        ctx.strokeStyle = 'green';
        // Left Top
        ctx.moveTo(x, y);
        ctx.lineTo(x, y + CELL_SIZE / 4);
        ctx.moveTo(x, y);
        ctx.lineTo(x + CELL_SIZE / 4, y);
        // Right Top
        ctx.moveTo(x + CELL_SIZE, y);
        ctx.lineTo(x + CELL_SIZE * 3 / 4, y);
        ctx.moveTo(x + CELL_SIZE, y);
        ctx.lineTo(x + CELL_SIZE, y + CELL_SIZE / 4);
        // Left Bot
        ctx.moveTo(x, y + CELL_SIZE);
        ctx.lineTo(x, y + CELL_SIZE * 3 / 4);
        ctx.moveTo(x, y + CELL_SIZE);
        ctx.lineTo(x + CELL_SIZE / 4, y + CELL_SIZE);
        // Right Bot
        ctx.moveTo(x + CELL_SIZE, y + CELL_SIZE);
        ctx.lineTo(x + CELL_SIZE * 3 / 4, y + CELL_SIZE);
        ctx.moveTo(x + CELL_SIZE, y + CELL_SIZE);
        ctx.lineTo(x + CELL_SIZE, y + CELL_SIZE * 3 / 4);
        ctx.stroke();
    };

    const handleReady = () => {
        const msgToSend = {
            username: user.username,
            ready: true
        }
        wsClientRef.current.sendMessage('/app/ready/' + roomId, JSON.stringify(msgToSend));
        setReady(true);
    }

    const handleUndoReady = () => {
        const msgToSend = {
            username: user.username,
            ready: false
        }
        wsClientRef.current.sendMessage('/app/ready/' + roomId, JSON.stringify(msgToSend));
        setReady(false);
    }

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
        drawMovingPiece(centerX, centerY);
    }, [centerX, centerY]);

    console.log("Before rendering in Board...");
    return (
        <div className="col-6">
            <SockJsClient url={ApiConstants.SOCKET_CONNECT_URL}
                          topics={['/room/' + roomId]}
                          onConnect={() => {
                              console.log("connected to /room/" + roomId);
                          }}
                          onDisconnect={() => {
                              console.log("Disconnected from /room/" + roomId);
                          }}
                          onMessage={(msg) => {
                              console.log("receive", msg);
                              if (msg.type === 'READY' && msg.data.username !== user.username) {
                                  if (isReady === true)
                                      setGameStarted(true);
                              }
                          }}
                          ref={wsClientRef}/>
            <div className="text-center">
                {isReady
                    ? <button className="btn btn-secondary" onClick={handleUndoReady}>Cancel...</button>
                    : <button className="btn btn-secondary" onClick={handleReady}>Ready...</button>
                }
            </div>
            <canvas ref={canvasRef}
                    className="border border-success"
                    width={BoardConstants.BOARD_WIDTH_SIZE}
                    height={BoardConstants.BOARD_HEIGHT_SIZE}
                    onClick={handleMove}
            />
        </div>
    )
}

export default Board;