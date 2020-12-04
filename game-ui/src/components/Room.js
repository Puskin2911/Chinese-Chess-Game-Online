import React from "react";
import Board from "./Board";
import Chat from "./chat/Chat";

export default function Room(props) {
    const roomId = props.roomId;
    const user = props.user;

    const [isGameStarted, setGameStarted] = React.useState(false);

    return (
        <div className="row justify-content-center">
            <Chat username={user.username} roomId={roomId}/>
            <Board roomId={roomId} isGameStarted={isGameStarted} setGameStarted={setGameStarted}/>
            <div className="col-3 border border-danger">Alo</div>
        </div>
    );

}