import React from "react";
import ApiConstants from "../../constants/ApiConstant";
import ConfirmModal from "../../common/ConfirmModal";
import UserProfile from "../../common/UserProfile";

export default class LeftBoard extends React.Component {
    constructor(props) {
        super(props);

        this.handleLeaveRoom = props.handleLeaveRoom;
        this.stompClient = props.stompClient;
        this.room = props.room;
        this.user = props.user;

        this.state = {
            isReady: false
        };
    }

    handleReady = () => {
        const msgToSend = {
            username: this.props.user.username,
            isReady: !this.state.isReady
        }
        this.stompClient.send(ApiConstants.READY_DESTINATION_SOCKET_URL(this.room.id), {}, JSON.stringify(msgToSend));

        this.setState({
            isReady: !this.state.isReady
        })
    }

    handleRequestLose = () => {
        this.stompClient.send("/room/" + this.room.id + "/request-lose", {}, {});
    }

    render() {
        return (
            <div className="col-2 mt-4 border border-danger text-center rounded">
                <UserProfile user={this.user}/>
                <div className="text-center my-5">
                    {this.props.isGameStarted
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
                                              cancel="Thôi" ok="Thua đê" handleOk={this.handleRequestLose}/>
                            </div>
                        </div>
                        : <button type="button" className="btn" onClick={this.handleReady}>
                            {this.state.isReady ?
                                <i className="far fa-2x fa-times-circle"/>
                                : <i className="fas fa-2x fa-chevron-circle-right"/>
                            }
                        </button>
                    }
                </div>
                <div className="text-center mt-5">
                    {this.props.isGameStarted
                        ? <div>
                            <button type="button" className="btn" data-toggle="modal" data-target="#confirmExitModal">
                                <i className="fas fa-2x fa-power-off"/>
                            </button>
                            <ConfirmModal id="confirmExitModal"
                                          title="Bạn sẽ thua ván này nếu thoát ra ngoài. Bạn có chắc muốn thoát không ?"
                                          cancel="Thôi" ok="Thoát đê" handleOk={this.handleLeaveRoom}/>
                        </div>
                        : <button type="button" className="btn" data-toggle="modal" data-target="#confirmExitModal">
                            <i className="fas fa-2x fa-power-off" onClick={this.handleLeaveRoom}/>
                        </button>
                    }
                    <h5 className="text-center rounded bg-white mx-4 py-1">
                        room: <code> {this.room.id}</code>
                    </h5>
                </div>
            </div>
        );
    }

}