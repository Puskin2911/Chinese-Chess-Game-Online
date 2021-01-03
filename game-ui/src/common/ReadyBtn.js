import React from "react";
import ApiConstants from "../constants/ApiConstant";

export default class ReadyBtn extends React.Component {
    constructor(props) {
        super(props);
        this.stompClient = props.stompClient;
        this.subscription = null;

        this.state = {
            isReady: false
        }
    }

    componentDidMount() {
        this.subscription = this.stompClient.subscribe("/room/" + this.props.room.id + "/ready", () => {
        });
    }

    componentWillUnmount() {
        if (this.subscription != null) {
            this.subscription.unsubscribe();
        }
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

    render() {
        const isReady = this.state.isReady;
        const displayText = isReady ? "Hủy" : "Sẵn sàng";
        
        return (
            <div>
                <button type="button" className="btn btn-outline-success p-0 bg-white rounded"
                        onClick={this.handleReady}>
                    <h5><code className="text-success px-1">{displayText}</code></h5>
                </button>
            </div>
        );
    }

}