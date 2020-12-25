import React from "react";
import gameApiService from "../services/GameApiService";
import Room from "./Room/Room";
import Lobby from "./Lobby";

export default function Home(props) {
    const [room, setRoom] = React.useState(null);

    const handleJoinRoom = () => {
        gameApiService.joinRoom()
            .then(res => {
                console.log("from handleJoinRoom", res);

                setRoom(res.data);
            })
            .catch(error => {
                console.log("from handleJoinRoom", error.response);
            });
    }

    const handleLeaveRoom = () => {
        gameApiService.leaveRoom(room.id)
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
                ? <Lobby user={props.user} handleJoinRoom={handleJoinRoom}/>
                : <Room room={room} user={props.user} handleLeaveRoom={handleLeaveRoom}/>
            }
        </div>
    );

}