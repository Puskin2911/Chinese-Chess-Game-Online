import React from "react";

export default function ChatBox(props) {
    const messages = props.messages;

    const handleDisplayMessage = () => {
        return (
            <div>
                {messages.map((msg, index) => {
                    const displayMessage = msg.data.username + ": " + msg.data.message;
                    return (<p key={index}> - {displayMessage}</p>);
                })}
            </div>
        );
    };

    return (
        <div className="pre-scrollable">
            {handleDisplayMessage()}
        </div>
    );
}