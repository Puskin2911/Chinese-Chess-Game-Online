import React from "react";
import {useHistory} from "react-router-dom";
import localStorageHelper from "../utils/LocalStorageHelper";
import Board from "./Board";
import GameSate from "../constants/GameStateConstants";

export default function Game() {
    const history = useHistory();

    const handleLogout = React.useCallback(() => {
        localStorageHelper.deleteCookie("loggedIn");
        history.replace("/login");
    }, [history]);

    const [isInGame, setInGame] = React.useState(false);

    const [gameState, setGameState] = React.useState(GameSate.OUTSIDE_ROOM);

    const handleJoinGame = () => {

    }

    return (
        <div className="container text-center">
            <div className="row justify-content-center">
                <div className="col-3 border border-danger">
                    Alo
                </div>
                <div className="col-6">
                    <div className="mb-0 border border-danger">
                        <button type="button" className="btn btn-dark" onClick={handleLogout}>Logout</button>
                        {!isInGame ?
                            <button type="button"
                                    className="btn btn-secondary"
                                    onClick={() => setInGame(true)}>
                                Start Game
                            </button>
                            : ""}
                    </div>
                    <Board isInGame={isInGame}/>
                </div>
                <div className="col-3 border border-danger">Alo</div>
            </div>
        </div>
    );
}