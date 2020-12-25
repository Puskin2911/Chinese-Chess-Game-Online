import React from "react";
import gameApiService from "../services/GameApiService";
import Room from "./Room/Room";
import Lobby from "./Lobby";

export default class Home extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            room: null
        }
    }

    handleJoinRoom = () => {
        gameApiService.joinRoom()
            .then(res => {
                console.log("from handleJoinRoom", res);

                this.setState({
                    room: res.data
                });
            })
            .catch(error => {
                console.log("from handleJoinRoom", error.response);
            });
    }

    handleLeaveRoom = () => {
        gameApiService.leaveRoom(this.state.room.id)
            .then(() => {
                    this.setState({
                        room: null
                    });
                }
            )
            .catch(error => {
                console.log("from handleLeaveRoom", error.response);
            });
    }

    render() {
        return (
            <div>
                {this.state.room == null
                    ? <Lobby user={this.props.user} handleJoinRoom={this.handleJoinRoom}/>
                    : <Room room={this.state.room} user={this.props.user} handleLeaveRoom={this.handleLeaveRoom}/>
                }
            </div>
        );
    }

}