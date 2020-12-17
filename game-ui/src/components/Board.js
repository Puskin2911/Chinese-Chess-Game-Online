import React from "react";
import imagePieceMap from "../common/ImagePieceLoader";
import Position from "../utils/Position";
import useDidUpdateEffect from "../common/CustomizeHook";
import BoardConstants from "../constants/BoardConstants";
import ApiConstants from "../constants/ApiConstant";
import SockJsClient from "react-stomp";
import canvasService from "../services/CanvasService";

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
                              console.log("from Board: receive", msg);
                              if (msg.type === 'READY' && msg.data.username !== user.username) {
                                  if (isReady === true)
                                      setGameStarted(true);
                              }
                          }}
                          ref={wsClientRef}/>
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