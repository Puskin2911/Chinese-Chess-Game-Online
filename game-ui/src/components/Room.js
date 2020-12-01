import React from "react";
import Board from "./Board";
import Chat from "./chat/Chat";

export default function Room(props) {
    const roomId = props.roomId;
    const user = props.user;

    const clientRef = React.useRef(null);

    return (
        <div className="row justify-content-center">
            <Chat username={user.username} roomId={roomId}/>
            <div className="col-6">
                <Board/>
            </div>
            <div className="col-3 border border-danger">Alo</div>
        </div>
    );

}