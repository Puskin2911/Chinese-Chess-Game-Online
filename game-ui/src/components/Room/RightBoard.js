import React from "react";
import ChatBox from "../Chat/ChatBox";
import SendMessage from "../Chat/SendMessage";
import UserProfile from "../../common/UserProfile";
import Clock from "../../common/Clock";

export default function RightBoard(props) {
    const user = props.user;
    const competitor = props.competitor;
    const stompClient = props.stompClient;
    const isGameStarted = props.isGameStarted;

    return (
        <div className="col-3 text-center rounded">
            <UserProfile user={competitor}/>
            <Clock isEnable={isGameStarted}/>
            <div className="mt-4">
                <ChatBox roomId={props.room.id} stompClient={stompClient}/>
                <SendMessage room={props.room} username={user.username} stompClient={stompClient}/>
            </div>
        </div>
    );
}