import React from "react";
import imageLoader from "./ImageLoader";

export default function UserProfile(props) {
    const user = props.user;

    return (
        <div className="mt-5">
            <button type="button" className="btn">
                <img src={imageLoader.userAvatar} width="80px" className="rounded-circle"
                     alt="User Avatar"/>
            </button>
            <div className="text-center mt-2 mb-5 mx-4">
                <h5 className="my-3">
                    {user != null
                        ? <span className="border border-danger rounded bg-white px-4 py-1">{user.username}</span>
                        : <span className="border border-danger rounded bg-white px-4 py-1">. . .</span>
                    }
                </h5>
                <h5 className="my-3">
                    <span className="border border-success rounded bg-white p-1">
                        <i className="fas fa-trophy"/>
                        <code className="p-2">
                            {user != null
                                ? <span>{user.elo}</span>
                                : "...."
                            }
                        </code>
                    </span>
                </h5>
            </div>
        </div>
    );
}