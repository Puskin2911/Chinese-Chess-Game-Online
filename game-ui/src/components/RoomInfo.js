import React from "react";
import imageLoader from "../common/ImageLoader";

export default function RoomInfo(props) {
    const user = props.user;
    const room = props.room;
    const isReady = props.isReady;
    const handleLeaveRoom = props.handleLeaveRoom;
    const handleReady = props.handleReady;
    const handleUndoReady = props.handleUndoReady;

    return (
        <div className="col-3 border border-danger text-center">
            <button type="button" className="btn">
                <img src={imageLoader.userAvatar} width="60%" className="img-thumbnail rounded-circle"
                     alt="User Avatar"/>
            </button>
            <label className="text-center rounded bg-white px-5">
                <h5>{user.username}</h5>
            </label>
            <h3 className="text-center">
                Room Id: {room.id}
            </h3>
            <button type="button" className="btn" onClick={handleLeaveRoom}>
                <i className="fas fa-2x fa-power-off"/>
            </button>

            {isReady === true
                ? <button type="button" className="btn" onClick={handleUndoReady}>
                    <i className="far fa-2x fa-times-circle"/>
                </button>
                : <button type="button" className="btn" onClick={handleReady}>
                    <i className="fas fa-2x fa-chevron-circle-right"/>
                </button>
            }
        </div>
    );
}