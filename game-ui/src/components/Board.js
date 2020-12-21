import React from "react";
import Position from "../utils/Position";
import {BOARD_HEIGHT_SIZE, BOARD_WIDTH_SIZE, CELL_SIZE} from "../constants/BoardConstants";
import canvasService from "../services/CanvasService";
import gameService from "../services/GameService";

export default class Board extends React.Component {

    constructor(props) {
        super(props);

        this.room = props.room;
        this.roomId = this.room.id;
        this.user = props.user;
        this.stompClient = props.stompClient;
        this.subscription = undefined;

        this.canvasRef = React.createRef();

        this.state = {
            boardStatus: undefined,
            movingPiece: "00000",
            clickingPosition: undefined,
            isMyTurn: false
        }
    }

    drawBoard = () => {
        const canvas = this.canvasRef.current;
        const ctx = canvas.getContext('2d');
        canvasService.clearBoard(canvas);
        canvasService.drawBlankBoard(ctx);
        canvasService.drawPieces(ctx, this.state.boardStatus);
    }

    handleMove = (event) => {
        if (!this.state.isMyTurn) return;

        const canvas = this.canvasRef.current;
        const rect = canvas.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;

        console.log('x: ' + x + '\ny: ' + y);

        const position = new Position(x, y);
        const xy = position.getXY();
        console.log(xy);

        if (xy === '-1') return;

        const boardStatus = this.state.boardStatus;
        const movingPiece = this.state.movingPiece;

        // Find piece when user click on board
        let index = boardStatus.indexOf(xy);
        let color = boardStatus.charAt(index + 2);
        const piece = boardStatus.slice(index, index + 5);

        const clickingPosition = {
            centerX: xy.slice(1) * (CELL_SIZE + 1) + 1,
            centerY: xy.slice(0, 1) * (CELL_SIZE + 1) + 1
        }

        if (movingPiece === '00000') {
            if (color !== '0') {
                this.setState({
                    movingPiece: piece,
                    clickingPosition: clickingPosition
                });
            }
        } else {
            let newBoardStatus;
            newBoardStatus = boardStatus.replaceAll(movingPiece, movingPiece.substring(0, 2) + '000');
            newBoardStatus = newBoardStatus.replaceAll(piece, piece.substring(0, 2) + movingPiece.substring(2));

            // TODO: Handle asynchronous display

            const move = movingPiece.slice(0, 2) + '_' + piece.slice(0, 2);
            // Send message to server.
            this.stompClient.send("/app/room/" + this.roomId + "/move", {}, JSON.stringify({
                roomId: this.roomId,
                move: move
            }));
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        console.log("Starting componentDidUpdate ...");
        const canvas = this.canvasRef.current;
        const ctx = canvas.getContext('2d');

        if (this.state.boardStatus !== undefined) {
            console.log("DRAW BOARD!!!!");
            this.drawBoard();
        }

        const clickingPosition = this.state.clickingPosition
        if (this.state.clickingPosition !== undefined) {
            canvasService.drawMovingPiece(ctx, clickingPosition.centerX, clickingPosition.centerY);
        }
    }

    componentDidMount() {
        console.log("Starting componentDidMount ...");

        this.subscription = this.stompClient.subscribe("/room/" + this.roomId + "/move", (payload) => {
            const gameDto = JSON.parse(payload.body);
            console.log("receive payload from Board: " + gameDto);

            const isMyTurnToUpdate = gameDto.nextTurnUsername === this.user.username;

            const boardStatusToUpdate = gameService.resolveBoardStatus(gameDto.boardStatus, this.props.isRedPlayer);

            this.setState({
                boardStatus: boardStatusToUpdate,
                isMyTurn: isMyTurnToUpdate,
                movingPiece: '00000'
            });
        });
    }

    componentWillUnmount() {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }

    render() {
        console.log("Before rendering in Board ...");
        return (
            <div className="col-6">
                <canvas ref={this.canvasRef}
                        className="border border-success"
                        width={BOARD_WIDTH_SIZE}
                        height={BOARD_HEIGHT_SIZE}
                        onClick={this.handleMove}
                />
            </div>
        );
    }

}