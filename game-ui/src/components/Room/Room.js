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
    const [isGameStarted, setGameStarted] = React.useState(false);

    const ws = new SockJs(ApiConstants.SOCKET_CONNECT_URL);
    const [stompClient] = React.useState(Stomp.over(ws));

    React.useEffect(() => {
        stompClient.connect({}, () => {
            setSocketConnected(true);

            stompClient.subscribe("/room/" + roomId, (payload) => {
                console.log("Receive payload from Room: " + payload.body);
            });

            stompClient.subscribe("/room/" + roomId + "/ready", (payload) => {
                console.log("READYYYYYY: " + JSON.parse(payload.body).isReady);
            });

            stompClient.subscribe("/room/" + roomId + "/game/start", () => {
                console.log("From Room receive signal start game!");
                setGameStarted(true);
            });
        });

        return () => {
            stompClient.disconnect(() => {
            });
        };
    }, []);

    if (!isSocketConnected) return <LoadingIndicator/>;

    return (
        <div className="container">
            <div className="row justify-content-center">
                <RoomInfo room={room} user={user}
                          stompClient={stompClient}
                          handleLeaveRoom={handleLeaveRoom}/>
                <Board room={room} user={user} stompClient={stompClient}/>
                <Chat username={user.username} room={room}
                      stompClient={stompClient}/>
            </div>
        </div>
    );

}