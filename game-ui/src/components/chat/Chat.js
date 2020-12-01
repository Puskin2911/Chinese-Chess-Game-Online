import React from "react";
import ApiConstants from "../../constants/ApiConstant";
import SockJsClient from "react-stomp";
import ChatBox from "./ChatBox";

export default function Chat(props) {
    const username = props.username;
    const roomId = props.roomId;

    const clientRef = React.useRef(null);

    const [messages, setMessages] = React.useState([]);
    const [typedMessage, setTypedMessage] = React.useState("");

    const handleSendMessage = () => {
        const msgToSend = {
            username: username,
            message: typedMessage
        }
        clientRef.current.sendMessage('/app/chat/' + roomId, JSON.stringify(msgToSend));
        setTypedMessage("");
    };

    console.log("Before rendering...")
    return (
        <div className="col-3 border border-danger bg-white">
            <SockJsClient url={ApiConstants.SOCKET_CONNECT_URL}
                          topics={['/room/' + roomId]}
                          onConnect={() => {
                              console.log("connected to /room/" + roomId);
                          }}
                          onDisconnect={() => {
                              console.log("Disconnected from /room/" + roomId);
                          }}
                          onMessage={(msg) => {
                              console.log("receive", msg);
                              const newMessages = [...messages];
                              newMessages.push(msg);
                              setMessages(newMessages);
                          }}
                          ref={clientRef}/>
            <h3 className="text-center">
                {username}
            </h3>
            <h3 className="text-center">
                Room Id: {roomId}
            </h3>
            <div className=" border border-danger">
                <div className="form-group">
                    <input className="form-control"
                           onChange={(e) => setTypedMessage(e.target.value)}
                           value={typedMessage}/>
                    <button onClick={handleSendMessage}>
                        <i className="fas fa-paper-plane"/>
                    </button>
                </div>
                <ChatBox messages={messages}/>
            </div>
        </div>
    );
}