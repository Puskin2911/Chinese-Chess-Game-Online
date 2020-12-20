import React from "react";
import ChatBox from "./ChatBox";
import SendMessage from "./SendMessage";

export default function Chat(props) {
    const username = props.username;
    const room = props.room;
    const roomId = room.id;
    const stompClient = props.stompClient;

    console.log("Before rendering in Chat...")
    return (
        <div className="col-3 border border-danger bg-white">
            <SendMessage room={room} username={username} stompClient={stompClient}/>
            <ChatBox roomId={roomId} stompClient={stompClient}/>
        </div>
    );
}