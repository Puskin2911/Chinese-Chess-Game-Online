import React from "react";
import ChatBox from "./ChatBox";
import SendMessage from "./SendMessage";
import imageLoader from "../../common/ImageLoader";

export default function RightBoard(props) {
    const user = props.user;
    const room = props.room;
    const roomId = room.id;
    const stompClient = props.stompClient;

    return (
        <div className="col-2 mt-4 border border-danger text-center rounded">
            <button type="button" className="btn">
                <img src={imageLoader.userAvatar} width="80px" className="rounded-circle"
                     alt="User Avatar"/>
            </button>
            <div className="text-center my-5 mb-3 mx-4">
                <h5 className="rounded bg-white p-1">{user.username}</h5>
                <h5 className="rounded bg-white p-1">
                    <i className="fas fa-trophy"/>
                    <code className="p-2">{user.elo}</code>
                </h5>
            </div>
            <ChatBox roomId={roomId} stompClient={stompClient}/>
            <SendMessage room={room} username={user.username} stompClient={stompClient}/>
        </div>
    );
}