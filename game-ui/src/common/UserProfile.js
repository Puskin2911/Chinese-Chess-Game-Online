import React from "react";
import imageLoader from "./ImageLoader";

export default function UserProfile(props) {
    const user = props.user;

    return (
        <div>
            <button type="button" className="btn">
                <img src={imageLoader.userAvatar} width="80px" className="rounded-circle"
                     alt="User Avatar"/>
            </button>
            <div className="text-center mt-2 mb-5 mx-4">
                <h5 className="rounded bg-white p-1">{user.username}</h5>
                <h5 className="rounded bg-white p-1">
                    <i className="fas fa-trophy"/>
                    <code className="p-2">{user.elo}</code>
                </h5>
            </div>
        </div>
    );
}