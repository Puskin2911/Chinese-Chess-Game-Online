import React from "react";
import Position from "../utils/Position";
import {BOARD_HEIGHT_SIZE, BOARD_WIDTH_SIZE} from "../constants/BoardConstants";
import canvasService from "../services/CanvasService";
import gameService from "../services/GameService";

export default class Board extends React.Component {

    constructor(props) {
        super(props);

        this.stompClient = props.stompClient;
        this.subscription = null;

        this.canvasRef = React.createRef();

        this.state = {
            boardStatus: null,
            movingPiece: null,
            clickingPiece: null,
            fromPiece: null,
            toPiece: null,
            availableMovePositions: [],
            isMyTurn: false,
            isGeneralChecking: false
        }
    }

    drawBoard = () => {
        const canvas = this.canvasRef.current;
        const ctx = canvas.getContext('2d');

        const user = this.props.user;
        const isRedPlayer = this.props.isRedPlayer;
        const boardStatus = this.state.boardStatus;

        canvasService.clearBoard(canvas);
        // canvasService.drawBlankBoard(ctx);
        canvasService.drawPieces(ctx, boardStatus, isRedPlayer);
        canvasService.drawAvailableMovePosition(ctx, this.state.availableMovePositions);
        canvasService.drawPieceBorder(ctx, this.state.clickingPiece, true);
        canvasService.drawPieceBorder(ctx, this.state.toPiece, isRedPlayer);
        canvasService.drawFromPiece(ctx, this.state.fromPiece, isRedPlayer);
        canvasService.drawGeneralChecking(ctx, gameService.resolveGeneralCheckingPosition(boardStatus), isRedPlayer);

        if (this.state.isGeneralChecking) {
            canvasService.drawGeneralCheckingEffect(ctx);
            setTimeout(() => {
                this.setState({
                    isGeneralChecking: false
                });
            }, 1500);
        }

        const gameResultCached = this.props.gameResultCached;
        if (gameResultCached != null) {
            if (user.username === gameResultCached.winner.username) {
                canvasService.drawWinnerEffect(ctx);
            } else {
                canvasService.drawLoserEffect(ctx);
            }
            if (gameResultCached.reason === "time_up") {
                canvasService.drawTimeUpNotification(ctx, gameResultCached.loser.username);
            } else if (gameResultCached.reason === "surrender") {
                canvasService.drawSurrenderNotification(ctx, gameResultCached.loser.username);
            }
        }

    }

    handleCancelMove = () => {
        this.setState({
            movingPiece: null,
            clickingPiece: null,
            availableMovePositions: []
        });
    }

    handleCacheFromAndToPiece = (moved) => {
        if (moved == null) {
            this.setState({
                fromPiece: null,
                toPiece: null
            });
            return;
        }

        this.setState({
            fromPiece: {
                centerX: moved.charAt(0),
                centerY: moved.charAt(1)
            },
            toPiece: {
                centerX: moved.charAt(3),
                centerY: moved.charAt(4)
            }
        });
    }

    handleMove = (event) => {
        if (!this.state.isMyTurn) return;

        const canvas = this.canvasRef.current;
        const rect = canvas.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;

        const position = new Position(x, y);
        const xy = position.getXY();
        console.log(xy);

        if (xy === '-1') return;

        const isRedPlayer = this.props.isRedPlayer;
        const boardStatus = gameService.resolveBoardStatus(this.state.boardStatus, isRedPlayer);
        const movingPiece = this.state.movingPiece;

        // Find piece when user click on board
        let index = boardStatus.indexOf("_" + xy);
        index++;
        let color = boardStatus.charAt(index + 2);
        const piece = boardStatus.slice(index, index + 5);

        const clickingPiece = {
            centerX: xy.charAt(0),
            centerY: xy.charAt(1)
        }

        if (movingPiece == null) {
            if (color !== '0' && gameService.isMyPiece(color, isRedPlayer)) {

                const availableMovePositionToSave = gameService
                    .getAvailableMovePosition(piece, boardStatus, isRedPlayer);

                this.setState({
                    movingPiece: piece,
                    clickingPiece: clickingPiece,
                    fromPiece: null,
                    toPiece: null,
                    availableMovePositions: availableMovePositionToSave
                });
            }
        } else {
            if (piece === movingPiece) {
                this.handleCancelMove();
                return;
            }

            let isAbleMove = false;
            const ableMoves = this.state.availableMovePositions;
            for (let i = 0; i < ableMoves.length; i++) {
                const ableMove = ableMoves[i];
                if (ableMove.centerX + "" + ableMove.centerY === "" + xy) {
                    isAbleMove = true;
                    break;
                }
            }
            if (!isAbleMove) {
                if (color !== '0' && gameService.isMyPiece(color, isRedPlayer)) {

                    const availableMovePositionToSave = gameService
                        .getAvailableMovePosition(piece, boardStatus, isRedPlayer);

                    this.setState({
                        movingPiece: piece,
                        clickingPiece: clickingPiece,
                        fromPiece: null,
                        toPiece: null,
                        availableMovePositions: availableMovePositionToSave
                    });
                }

                return;
            }

            // TODO: Handle asynchronous display
            let move = movingPiece.slice(0, 2) + '_' + piece.slice(0, 2);


            move = gameService.resolveMove(move, isRedPlayer);

            // Send message to server.
            this.stompClient.send("/app/room/" + this.props.room.id + "/move", {}, JSON.stringify({
                roomId: this.props.room.id,
                move: move
            }));
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.state.boardStatus !== null) {
            this.drawBoard();
        }
    }

    componentDidMount() {
        this.subscription = this.stompClient.subscribe("/room/" + this.props.room.id + "/move", (payload) => {
            const gameDto = JSON.parse(payload.body);
            const isMyTurnToUpdate = gameDto.nextTurnUsername === this.props.user.username;
            this.handleCacheFromAndToPiece(gameDto.moved);

            this.setState({
                boardStatus: gameDto.boardStatus,
                isMyTurn: isMyTurnToUpdate,
                clickingPiece: null,
                movingPiece: null,
                availableMovePositions: [],
                isGeneralChecking: gameDto.isGeneralChecking
            });
        });
    }

    componentWillUnmount() {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }

    render() {
        return (
            <div className="col-6 text-center mt-4">
                <canvas id="board" ref={this.canvasRef}
                        width={BOARD_WIDTH_SIZE}
                        height={BOARD_HEIGHT_SIZE}
                        onClick={this.handleMove}
                />
            </div>
        );
    }

}