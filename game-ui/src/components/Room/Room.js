import React from "react";
import Board from "../Board";
import Chat from "../Chat/Chat";
import ApiConstants from "../../constants/ApiConstant";
import RoomInfo from "./RoomInfo";
import SockJs from "sockjs-client";
import Stomp from "stompjs";
import LoadingIndicator from "../../common/LoadingIndicator";

export default function Room(props) {
    const room = props.room;
    const roomId = room.id;
    const user = props.user;
    const handleLeaveRoom = props.handleLeaveRoom;

    const [isSocketConnected, setSocketConnected] = React.useState(false);
    const [isReady, setReady] = React.useState(false);
    const [isGameStarted, setGameStarted] = React.useState(false);

    const ws = new SockJs(ApiConstants.SOCKET_CONNECT_URL);
    const [stompClient] = React.useState(Stomp.over(ws));

    React.useEffect(() => {
        stompClient.connect({}, () => {
            setSocketConnected(true);
            stompClient.subscribe("/room/" + roomId, (payload) => {
                console.log("Receive payload from Room: " + payload.body);
            });
        });

        return () => {
            stompClient.disconnect(() => {
                alert("You just left room");
            });
        };
    }, []);

    const handleReady = () => {
        const msgToSend = {
            username: user.username,
            ready: true
        }
        stompClient.send("/app/room/" + roomId + "/ready", {}, JSON.stringify(msgToSend));
        setReady(true);
    }

    const handleUndoReady = () => {
        const msgToSend = {
            username: user.username,
            ready: false
        }
        stompClient.send("/app/room/" + roomId + "/ready", {}, JSON.stringify(msgToSend));
        setReady(false);
    }

    console.log("Before rendering in Room ...");
    if (!isSocketConnected) return <LoadingIndicator/>;

    console.log("WS Connected");
    return (
        <div className="container">
            <div className="row justify-content-center">
                <RoomInfo room={room} user={user} isReady={isReady}
                          stompClient={stompClient}
                          handleLeaveRoom={handleLeaveRoom}
                          handleReady={handleReady}
                          handleUndoReady={handleUndoReady}/>
                <Board room={room} user={user} isGameStarted={isGameStarted}
                       stompClient={stompClient}
                       setGameStarted={setGameStarted}/>
                <Chat username={user.username} room={room}
                      stompClient={stompClient}/>
            </div>
        </div>
    );

}