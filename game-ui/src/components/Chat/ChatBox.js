import React from "react";
import ApiConstants from "../../constants/ApiConstant";

export default class ChatBox extends React.Component {
    constructor(props) {
        super(props);

        this.state = {messages: []};
    }

    componentDidMount() {
        const subscription = this.props.stompClient.subscribe(ApiConstants.CHAT_SOCKET_URL(this.props.roomId), (payload) => {
            console.log("receive payload from ChatBox: " + payload.body);
            const msg = JSON.parse(payload.body);
            const newMessages = [...this.state.messages];
            newMessages.push(msg);
            this.setState({messages: newMessages});
        });
        return () => {
            subscription.unsubscribe();
        };
    }

    showMessage() {
        return this.state.messages.map((message, index) => {
            const displayMessage = "- " + message.username + ": " + message.message;
            return (<p key={index}>{displayMessage}</p>);
        });
    }

    render() {
        return (
            <div className="bg-white rounded text-left p-2 h-200px pre-scrollable">
                {this.showMessage()}
            </div>
        );
    }

}