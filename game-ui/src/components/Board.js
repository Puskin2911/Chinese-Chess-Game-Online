import React from "react";
import Position from "../utils/Position";
import {BOARD_DEFAULT_STATUS, BOARD_HEIGHT_SIZE, BOARD_WIDTH_SIZE, CELL_SIZE} from "../constants/BoardConstants";
import canvasService from "../services/CanvasService";

export default class Board extends React.Component {

    constructor(props) {
        super(props);

        this.room = props.room;
        this.roomId = this.room.id;
        this.user = props.user;
        this.isGameStarted = props.isGameStarted;
        this.setGameStarted = props.setGameStarted;
        this.stompClient = props.stompClient;
        this.subscription = undefined;

        this.canvasRef = React.createRef();

        this.state = {
            boardStatus: BOARD_DEFAULT_STATUS,
            movingPiece: "00000",
            centerX: 0,
            centerY: 0
        }

        this.handleMove = this.handleMove.bind(this);
    }

    drawBoard = () => {
        const canvas = this.canvasRef.current;
        const ctx = canvas.getContext('2d');
        canvasService.clearBoard(canvas);
        canvasService.drawBlankBoard(ctx);
        canvasService.drawPieces(ctx, this.state.boardStatus);
    }

    handleMove(event) {
        const canvas = this.canvasRef.current;
        const rect = canvas.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;

        console.log('x: ' + x + '\ny: ' + y);

        const position = new Position(x, y);
        const xy = position.getXY();
        console.log(xy);
        if (xy !== '-1') {
            const boardStatus = this.state.boardStatus;
            const movingPiece = this.state.movingPiece;

            let index = boardStatus.indexOf(xy);
            let color = boardStatus.charAt(index + 2);
            const piece = boardStatus.slice(index, index + 5);

            this.setState({
                centerX: xy.slice(1) * (CELL_SIZE + 1) + 1,
                centerY: xy.slice(0, 1) * (CELL_SIZE + 1) + 1
            });
            if (movingPiece === '00000') {
                if (color !== '0') {
                    console.log("Picked " + piece);
                    this.setState({movingPiece: piece});
                    console.log(this.centerX, this.centerY);
                }
            } else {
                let newBoardStatus;
                newBoardStatus = boardStatus.replaceAll(movingPiece, movingPiece.substring(0, 2) + '000');
                newBoardStatus = newBoardStatus.replaceAll(piece, piece.substring(0, 2) + movingPiece.substring(2));

                console.log(canvas.clientWidth, canvas.clientHeight);
                console.log(this.state.centerX, this.state.centerY);

                this.setState({
                    movingPiece: "00000",
                    boardStatus: newBoardStatus
                });
            }
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.isGameStarted) {
            this.drawBoard();
        }
    }

    componentDidMount() {
        if (this.isGameStarted) {
            this.drawBoard();
        }

        this.subscription = this.stompClient.subscribe("/move/room/" + this.roomId, (payload) => {
            alert("got message with body " + payload.body);
            console.log("receive payload from Board: " + JSON.parse(payload.body));
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