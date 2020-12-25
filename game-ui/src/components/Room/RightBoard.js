import React from "react";
import ChatBox from "../Chat/ChatBox";
import SendMessage from "../Chat/SendMessage";
import UserProfile from "../../common/UserProfile";

export default function RightBoard(props) {
    const user = props.user;
    const competitor = props.competitor;
    const stompClient = props.stompClient;
    const isGameStarted = props.isGameStarted;

    return (
        <div className="col-2 mt-4 border border-danger text-center rounded">
            <UserProfile user={competitor}/>
            {isGameStarted
                ? <div className="my-5">
                    Clock Here
                </div>
                : ""
            }
            <ChatBox roomId={props.room.id} stompClient={stompClient}/>
            <SendMessage room={props.room} username={user.username} stompClient={stompClient}/>
        </div>
    );
}