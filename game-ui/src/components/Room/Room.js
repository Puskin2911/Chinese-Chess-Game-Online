import React from "react";
import Board from "../Board";
import RightBoard from "./RightBoard";
import ApiConstants from "../../constants/ApiConstant";
import LeftBoard from "./LeftBoard";
import SockJs from "sockjs-client";
import Stomp from "stompjs";
import LoadingIndicator from "../../common/LoadingIndicator";

export default class Room extends React.Component {
    constructor(props) {
        super(props);

        this.handleLeaveRoom = props.handleLeaveRoom;
        this.ws = new SockJs(ApiConstants.SOCKET_CONNECT_URL);
        this.stompClient = Stomp.over(this.ws);

        this.state = {
            isSocketConnected: false,
            isGameStarted: false,
            isRedPlayer: false,
            competitor: null
        }
    }

    componentDidMount() {
        document.title = "Chinese Chess Game | Room";

        const roomId = this.props.room.id;
        const user = this.props.user;
        const stompClient = this.stompClient;
        stompClient.connect({}, () => {
            this.setState({
                isSocketConnected: true
            });

            stompClient.subscribe("/room/" + roomId, (payload) => {
                console.log("Receive payload from Room: " + payload.body);
                this.setState({
                    competitor: JSON.parse(payload.body)
                });
            });

            stompClient.subscribe("/room/" + roomId + "/ready", (payload) => {
                console.log("READY: " + JSON.parse(payload.body));
            });

            stompClient.subscribe("/room/" + roomId + "/game/start", (payload) => {
                console.log("From Room receive signal start game!");
                const gameStart = JSON.parse(payload.body);

                this.setState({
                    isGameStarted: true,
                    isRedPlayer: gameStart.redPlayerUsername === user.username
                });
            });
        });
    }

    componentWillUnmount() {
        this.stompClient.disconnect(() => {

        });
    }


    render() {
        if (!this.state.isSocketConnected) return <LoadingIndicator/>;

        const room = this.props.room;
        const user = this.props.user;
        return (
            <div className="container-fluid vh-100">
                <div className="row justify-content-center">
                    <LeftBoard room={room} user={user} isGameStarted={this.state.isGameStarted}
                               stompClient={this.stompClient}
                               handleLeaveRoom={this.handleLeaveRoom}/>
                    <Board room={room} user={user} stompClient={this.stompClient}
                           isRedPlayer={this.state.isRedPlayer}/>
                    <RightBoard user={user} competitor={this.state.competitor} room={room}
                                isGameStarted={this.state.isGameStarted}
                                stompClient={this.stompClient}/>
                </div>
            </div>
        );
    }

}