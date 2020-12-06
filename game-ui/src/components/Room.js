import React from "react";
import Board from "./Board";
import Chat from "./chat/Chat";

export default function Room(props) {
    const room = props.room;
    const user = props.user;

    const [isGameStarted, setGameStarted] = React.useState(false);

    return (
        <div className="row justify-content-center">
            <div className="col-3 border border-danger">
                <h3 className="text-center">
                    {user.username}
                </h3>
                <h3 className="text-center">
                    Room Id: {room.id}
                </h3>
            </div>
            <Board room={room} user={user} isGameStarted={isGameStarted} setGameStarted={setGameStarted}/>
            <Chat username={user.username} room={room}/>
        </div>
    );

}