import React from "react";
import localStorageHelper from "../utils/LocalStorageHelper";
import {useHistory} from "react-router-dom";
import imageLoader from "../common/ImageLoader";

export default function Lobby(props) {
    document.title = "Chinese Chess Game | Lobby";

    const history = useHistory();

    const user = props.user;
    const handleJoinRoom = props.handleJoinRoom;

    const handleLogout = React.useCallback(() => {
        console.log("from handleLogout");

        localStorageHelper.deleteCookie("loggedIn");
        history.replace("/login");
    }, [history]);

    return (
        <div className="container vh-100">
            <div className="row justify-content-around">
                <div className="col-4 text-left mt-2">
                    <img src={imageLoader.userAvatar} width="50px" className="rounded-circle"
                         alt="avatar"/>
                    <label className="text-center rounded bg-white py-1 px-2 ml-2 mb-0">
                        <h5>
                            <span>{user.username}</span>
                            <i className="fas fa-trophy pl-4"/>
                            <code className="p-2">{user.elo}</code>
                        </h5>
                    </label>
                </div>
                <div className="col-4 text-right mt-2">
                    <button type="button" className="btn bg-white p-1 mx-1">
                        <i className="fas fa-2x fa-user-friends"/>
                    </button>
                    <button type="button" className="btn bg-white p-1 mx-1">
                        <i className="fas fa-2x fa-book-open"/>
                    </button>
                    <button type="button" className="btn bg-white p-1 mx-1" onClick={handleLogout}>
                        <i className="fas fa-2x fa-sign-out-alt"/>
                    </button>
                </div>
            </div>
            <div className="row justify-content-around pt-5 mt-5">
                <div className="col-3 text-center border">
                    <button type="button" className="btn btn-danger" onClick={handleJoinRoom}>
                        Go to Solo
                    </button>
                </div>
                <div className="col-3 text-center border">
                    <button type="submit" className="btn btn-secondary">
                        Go to watching
                    </button>
                </div>
            </div>
        </div>
    );
}