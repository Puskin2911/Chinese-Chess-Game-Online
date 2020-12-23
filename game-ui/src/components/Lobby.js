import React from "react";
import localStorageHelper from "../utils/LocalStorageHelper";
import {useHistory} from "react-router-dom";

export default function Lobby(props) {
    document.title = "Chinese Chess Game | Lobby";

    const history = useHistory();

    const handleJoinRoom = props.handleJoinRoom;

    const handleLogout = React.useCallback(() => {
        console.log("from handleLogout");

        localStorageHelper.deleteCookie("loggedIn");
        history.replace("/login");
    }, [history]);

    return (
        <div className="container border vh-100">
            <div className="row justify-content-around">
                <div className="col-4 text-left bg-white">
                    User Info here
                </div>
                <div className="col-4 text-right bg-white">
                    <h5 className="d-inline">Something</h5>
                    <button type="button" className="btn">
                        <i className="fas fa-2x fa-user-friends"/>
                    </button>
                    <button type="button" className="btn">
                        <i className="fas fa-2x fa-book-open"/>
                    </button>
                    <button type="button" className="btn" onClick={handleLogout}>
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