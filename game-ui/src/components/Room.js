import React from "react";
import Board from "./Board";

export default function Room(props) {
    const roomId = props.roomId;
    const user = props.user;

    return (
        <div className="row justify-content-center">
            <div className="col-3 border border-danger">
                <h3 className="text-center">
                    {user.username}
                </h3>
                <h3 className="text-center">
                    Room Id: {roomId}
                </h3>
            </div>
            <div className="col-6">
                <Board/>
            </div>
            <div className="col-3 border border-danger">Alo</div>
        </div>
    );

}