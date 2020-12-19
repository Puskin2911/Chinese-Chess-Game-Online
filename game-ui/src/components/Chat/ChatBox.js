import React from "react";
import ApiConstants from "../../constants/ApiConstant";

export default class ChatBox extends React.Component {
    constructor(props) {
        super(props);

        this.state = {messages: []};
    }

    componentDidMount() {
        const subscription = this.props.stompClient.subscribe(ApiConstants.CHAT_SOCKET_URL(this.props.roomId), (payload) => {
            console.log("receive payload from Chat: " + payload.body);
            const msg = JSON.parse(payload.body);
            if (msg.type === "CHAT") {
                const newMessages = [...this.state.messages];
                newMessages.push(msg.data);
                this.setState({messages: newMessages});
            }
        });
        return () => {
            subscription.unsubscribe();
        };
    }

    showMessage() {
        return this.state.messages.map((message, index) => {
            const displayMessage = "- " + message.username + ": " + message.message;
            return (<div key={index}>{displayMessage}</div>);
        });
    }

    render() {
        return (
            <div>
                {this.showMessage()}
            </div>
        );
    }

}