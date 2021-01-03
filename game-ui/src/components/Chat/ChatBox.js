import React from "react";
import ApiConstants from "../../constants/ApiConstant";

export default class ChatBox extends React.Component {
    constructor(props) {
        super(props);

        this.state = {messages: []};
    }

    componentDidMount() {
        const subscription = this.props.stompClient.subscribe(ApiConstants.CHAT_SOCKET_URL(this.props.roomId), (payload) => {
            const msg = JSON.parse(payload.body);
            const newMessages = [...this.state.messages];
            newMessages.push(msg);
            this.setState({messages: newMessages});
        });
        return () => {
            subscription.unsubscribe();
        };
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        const msgContainer = document.getElementById("message-container");
        msgContainer.scrollTop = msgContainer.scrollHeight;
    }

    showMessage() {
        return this.state.messages.map((message, index) => {
            const displayMessage = "- " + message.username + ": " + message.message;
            return (<p key={index} className="my-1 text-white">{displayMessage}</p>);
        });
    }

    render() {
        return (
            <div id="message-container" className="border rounded text-left p-2 h-200px overflow-auto">
                {this.showMessage()}
            </div>
        );
    }

}