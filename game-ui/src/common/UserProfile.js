import React from "react";
import imageLoader from "../imageLoader/ImageLoader";

export default class UserProfile extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const user = this.props.user;
        return (
            <div className="mt-4">
                {user == null
                    ? <button type="button" className="btn">
                        <img src={imageLoader.userAvatar} width="80px" className="rounded-circle"
                             alt="User Avatar"/>
                    </button>
                    : <div>
                        <button type="button" className="btn" data-toggle="modal"
                                data-target={"#modal-user-profile-" + user.username}>
                            <img src={imageLoader.userAvatar} width="80px" className="rounded-circle"
                                 alt="User Avatar"/>
                        </button>
                        <div className="modal fade" id={"modal-user-profile-" + user.username} tabIndex="-1"
                             role="dialog" aria-hidden="true">
                            <div className="modal-dialog" role="document">
                                <div className="modal-content bg-dark">
                                    <div className="border-bottom p-3">
                                        <h3>
                                            <span className="text-center bg-white rounded p-1 text-success">
                                                Chiến tích
                                            </span>
                                        </h3>
                                    </div>
                                    <div className="modal-body">
                                        <h5 className="m-4">
                                            <code className="border rounded bg-white p-2">{user.username}</code>
                                        </h5>
                                        <h5 className="m-4">
                                            <code className="border rounded bg-white p-2">{user.elo}</code>
                                        </h5>
                                    </div>
                                    <div className="border-top text-center p-3">
                                        <button type="button" className="btn btn-secondary" data-dismiss="modal">Close
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                }

                <div className="text-center mt-2 mb-5 mx-4">
                    <h5 className="my-3">
                        {user != null
                            ? <span className="rounded bg-white px-4 py-1">{user.username}</span>
                            : <span className="rounded bg-white px-4 py-1">. . .</span>
                        }
                    </h5>
                    <h5 className="my-3">
                    <span className="rounded bg-white p-1">
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

}