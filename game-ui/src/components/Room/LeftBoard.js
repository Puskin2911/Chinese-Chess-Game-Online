import React from "react";
import imageLoader from "../../common/ImageLoader";
import ApiConstants from "../../constants/ApiConstant";

export default function RoomInfo(props) {
    const user = props.user;
    const room = props.room;
    const roomId = room.id;
    const [isReady, setReady] = React.useState(false);
    const stompClient = props.stompClient;
    const handleLeaveRoom = props.handleLeaveRoom;

    const handleReady = () => {
        const msgToSend = {
            username: user.username,
            isReady: !isReady
        }
        stompClient.send(ApiConstants.READY_DESTINATION_SOCKET_URL(roomId), {}, JSON.stringify(msgToSend));
        setReady(!isReady);
    }

    return (
        <div className="col-2 mt-4 border border-danger text-center">
            <div className="container-fluid">
                <div className="row">
                    <div className="col-3">
                        <button type="button" className="btn float-left">
                            <img src={imageLoader.userAvatar} width="80px" className="rounded-circle"
                                 alt="User Avatar"/>
                        </button>
                    </div>
                    <div className="col-7">
                        <label className="text-center mt-3">
                            <h5 className="rounded bg-white p-1">{user.username}</h5>
                            <h5 className="rounded bg-white p-1">
                                <i className="fas fa-trophy"/>
                                <code className="p-2">{user.elo}</code>
                            </h5>
                        </label>
                    </div>
                </div>
                <h3 className="text-center">
                    Room Id: {room.id}
                </h3>
                <button type="button" className="btn" onClick={handleLeaveRoom}>
                    <i className="fas fa-2x fa-power-off"/>
                </button>
                <button type="button" className="btn" onClick={handleReady}>
                    {isReady ?
                        <i className="far fa-2x fa-times-circle"/>
                        : <i className="fas fa-2x fa-chevron-circle-right"/>
                    }
                </button>
            </div>
        </div>
    );

}