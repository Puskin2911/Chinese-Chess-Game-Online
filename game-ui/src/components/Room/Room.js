import React from "react";
import Board from "../Board";
import Chat from "../Chat/Chat";
import ApiConstants from "../../constants/ApiConstant";
import SockJsClient from "react-stomp";
import RoomInfo from "./RoomInfo";

export default function Room(props) {
    const room = props.room;
    const roomId = room.id;
    const user = props.user;
    const handleLeaveRoom = props.handleLeaveRoom;

    const stompClient = React.useRef(null);

    const [isReady, setReady] = React.useState(false);
    const [isGameStarted, setGameStarted] = React.useState(false);

    const handleReady = () => {
        const msgToSend = {
            username: user.username,
            ready: true
        }
        stompClient.current.sendMessage('/app/ready/' + roomId, JSON.stringify(msgToSend));
        setReady(true);
    }

    const handleUndoReady = () => {
        const msgToSend = {
            username: user.username,
            ready: false
        }
        stompClient.current.sendMessage('/app/ready/' + roomId, JSON.stringify(msgToSend));
        setReady(false);
    }

    console.log("Before rendering in Room ...");
    return (
        <div className="container">
            <div className="row justify-content-center">
                <RoomInfo room={room} user={user} isReady={isReady} handleLeaveRoom={handleLeaveRoom}
                          handleReady={handleReady}
                          handleUndoReady={handleUndoReady}/>
                <Board room={room} user={user} isGameStarted={isGameStarted} setGameStarted={setGameStarted}/>
                <Chat username={user.username} room={room}/>
                <SockJsClient url={ApiConstants.SOCKET_CONNECT_URL}
                              topics={['/room/' + room.id]}
                              onConnect={() => {
                                  console.log("connected to /room/" + roomId);
                              }}
                              onDisconnect={() => {
                                  console.log("Disconnected from /room/" + roomId);
                              }}
                              onMessage={(msg) => {
                                  console.log("from Room: receive", msg);
                              }}
                              ref={stompClient}/>
            </div>
        </div>
    );

}