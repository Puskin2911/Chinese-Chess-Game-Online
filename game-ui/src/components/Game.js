import React from "react";
import gameService from "../services/GameService";
import Room from "./Room/Room";
import Lobby from "./Lobby";

export default function Game(props) {

    const [room, setRoom] = React.useState(null);

    const handleJoinRoom = () => {
        gameService.joinRoom()
            .then(res => {
                console.log("from handleJoinRoom", res);

                setRoom(res.data);
            })
            .catch(error => {
                console.log("from handleJoinRoom", error.response);
            });
    }

    const handleLeaveRoom = () => {
        gameService.leaveRoom(room.id)
            .then(() => {
                    setRoom(null);
                }
            )
            .catch(error => {
                console.log("from handleLeaveRoom", error.response);
            });
    }

    return (
        <div>
            {room == null
                ? <Lobby handleJoinRoom={handleJoinRoom}/>
                : <Room room={room} user={props.user} handleLeaveRoom={handleLeaveRoom}/>
            }
        </div>
    );

}