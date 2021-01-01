import React from "react";
import ApiConstants from "../../constants/ApiConstant";
import ConfirmModal from "../../common/ConfirmModal";
import UserProfile from "../../common/UserProfile";
import Clock from "../../common/Clock";

export default class LeftBoard extends React.Component {
    constructor(props) {
        super(props);

        this.handleLeaveRoom = props.handleLeaveRoom;
        this.stompClient = props.stompClient;

        this.state = {
            isReady: false
        };
    }

    handleReady = () => {
        const msgToSend = {
            username: this.props.user.username,
            isReady: !this.state.isReady
        }
        this.stompClient.send(ApiConstants.READY_DESTINATION_SOCKET_URL(this.props.room.id), {}, JSON.stringify(msgToSend));

        this.setState({
            isReady: !this.state.isReady
        })
    }

    handleSurrenderRequest = () => {
        this.stompClient.send("/app/room/" + this.props.room.id + "/game/surrender-request", {}, {});
    }

    handleDrawRequest = () => {
        this.stompClient.send("/app/room/" + this.props.room.id + "/game/draw-request", {}, {});
    }

    render() {
        return (
            <div className="col-3 text-center rounded">
                <UserProfile user={this.props.user}/>
                <Clock isEnable={this.props.isGameStarted}/>
                <div className="text-center mt-4">
                    {this.props.isGameStarted
                        ? <div>
                            <div className="my-4">
                                <button type="button" data-toggle="modal" data-target="#confirmDrawModal"
                                        className="btn border-success bg-white mr-1">Hòa đê
                                </button>
                                <ConfirmModal id="confirmDrawModal" title="Bạn có chắc muốn cầu hòa không ?"
                                              cancel="Thôi" ok="Hòa đê" handleOk={this.handleDrawRequest}/>
                                <button type="button" data-toggle="modal" data-target="#confirmLoseModal"
                                        className="btn border-danger bg-white ml-1">Xin thua
                                </button>
                                <ConfirmModal id="confirmLoseModal" title="Bạn có chắc muốn xin thua không ?"
                                              cancel="Thôi" ok="Thua đê" handleOk={this.handleSurrenderRequest}/>
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
                    <h5 className="text-center mt-2">
                        <span className="rounded bg-white p-1">
                            <code> ROOM {this.props.room.id}</code>
                        </span>
                    </h5>
                </div>
            </div>
        );
    }

}