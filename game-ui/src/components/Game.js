import React from "react";
import {useHistory} from "react-router-dom";
import localStorageHelper from "../utils/LocalStorageHelper";
import gameService from "../service/GameService";
import Room from "./Room";

export default function Game(props) {
    const history = useHistory();

    const handleLogout = React.useCallback(() => {
        localStorageHelper.deleteCookie("loggedIn");
        history.replace("/login");
    }, [history]);

    const [room, setRoom] = React.useState(null);

    const handleJoinRoom = () => {
        gameService.joinRoom()
            .then(res => {
                console.log("from handleJoinRoom", res);

                console.log(res.data)
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
        <div className="container text-center">
            <div className="row justify-content-center">
                <div className="col-6">
                    <button type="button" className="btn btn-secondary" onClick={handleLogout}>
                        Logout
                    </button>
                    {room == null
                        ? <button type="button" className="btn btn-secondary" onClick={handleJoinRoom}>
                            Join Room Now
                        </button>
                        : <button type="button" className="btn btn-secondary" onClick={handleLeaveRoom}>
                            Leave Room Now
                        </button>
                    }
                </div>
            </div>
            {room == null ? null : <Room roomId={room.id} user={props.user}/>}
        </div>
    );

}