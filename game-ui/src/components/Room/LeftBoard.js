import React from "react";
import imageLoader from "../../common/ImageLoader";
import ApiConstants from "../../constants/ApiConstant";
import ConfirmModal from "../../common/ConfirmModal";

export default function LeftBoard(props) {
    const user = props.user;
    const room = props.room;
    const roomId = room.id;
    const [isReady, setReady] = React.useState(false);
    const stompClient = props.stompClient;
    const handleLeaveRoom = props.handleLeaveRoom;
    const isGameStarted = props.isGameStarted;

    const handleReady = () => {
        const msgToSend = {
            username: user.username,
            isReady: !isReady
        }
        stompClient.send(ApiConstants.READY_DESTINATION_SOCKET_URL(roomId), {}, JSON.stringify(msgToSend));
        setReady(!isReady);
    }

    return (
        <div className="col-2 mt-4 border border-danger text-center rounded">
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
            <div className="text-center my-5">
                {isGameStarted
                    ? <div>
                        <div className="my-5">
                            Clock Here
                        </div>
                        <div className="my-5">
                            <button type="button" data-toggle="modal" data-target="#confirmDrawModal"
                                    className="btn border-success bg-white mr-1">Hòa đê
                            </button>
                            <ConfirmModal id="confirmDrawModal" title="Bạn có chắc muốn cầu hòa không ?"
                                          cancel="Thôi" ok="Hòa đê" handleOk=""/>
                            <button type="button" data-toggle="modal" data-target="#confirmLoseModal"
                                    className="btn border-danger bg-white ml-1">Xin thua
                            </button>
                            <ConfirmModal id="confirmLoseModal" title="Bạn có chắc muốn xin thua không ?"
                                          cancel="Thôi" ok="Thua đê" handleOk=""/>
                        </div>
                    </div>
                    : <button type="button" className="btn" onClick={handleReady}>
                        {isReady ?
                            <i className="far fa-2x fa-times-circle"/>
                            : <i className="fas fa-2x fa-chevron-circle-right"/>
                        }
                    </button>
                }
            </div>
            <div className="text-center mt-5">
                {isGameStarted
                    ? <div>
                        <button type="button" className="btn" data-toggle="modal" data-target="#confirmExitModal">
                            <i className="fas fa-2x fa-power-off"/>
                        </button>
                        <ConfirmModal id="confirmExitModal"
                                      title="Bạn sẽ thua ván này nếu thoát ra ngoài. Bạn có chắc muốn thoát không ?"
                                      cancel="Thôi" ok="Thoát đê" handleOk={handleLeaveRoom}/>
                    </div>
                    : <button type="button" className="btn" data-toggle="modal" data-target="#confirmExitModal">
                        <i className="fas fa-2x fa-power-off" onClick={handleLeaveRoom}/>
                    </button>
                }
                <h5 className="text-center rounded bg-white mx-4 py-1">
                    room: <code> {room.id}</code>
                </h5>
            </div>
        </div>
    );

}