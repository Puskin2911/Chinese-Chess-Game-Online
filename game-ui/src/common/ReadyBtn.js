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
        return (
            <div>
                <button type="button" className="btn p-0 bg-white rounded" title="Sẵn sàng" onClick={this.handleReady}>
                    <span className="text-success">
                        {isReady ?
                            <i className="far fa-2x fa-times-circle"/>
                            : <i className="fas fa-2x fa-chevron-circle-right"/>
                        }
                    </span>
                </button>
            </div>
        );
    }

}