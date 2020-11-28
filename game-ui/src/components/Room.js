import React from "react";
import SockJsClient from 'react-stomp';
import Board from "./Board";
import ApiConstants from "../constants/ApiConstant";

export default function Room(props) {
    const roomId = props.roomId;
    const user = props.user;

    const [messages, setMessages] = React.useState([]);
    const [typedMessage, setTypedMessage] = React.useState("");
    const [username, setUsername] = React.useState(user.username);

    const clientRef = React.useRef(null);

    const handleDisplayMessage = () => {
        return (
            <div>
                {messages.map(msg => {
                    console.log(msg.username);
                    return (
                        <div>
                            {username === msg.username ?
                                <div>
                                    <p className="">{msg.username} : </p><br/>
                                    <p>{msg.message}</p>
                                </div> :
                                <div>
                                    <p className="">{msg.username} : </p><br/>
                                    <p>{msg.message}</p>
                                </div>
                            }
                        </div>)
                })}
            </div>
        );
    }

    const handleSendMessage = () => {
        console.log(clientRef.current);
        clientRef.current.sendMessage('/app/chat/' + roomId, JSON.stringify({
            username: username,
            message: typedMessage
        }));
    };

    return (
        <div className="row justify-content-center">
            <div className="col-3 border border-danger">
                <h3 className="text-center">
                    {user.username}
                </h3>
                <h3 className="text-center">
                    Room Id: {roomId}
                </h3>
                <div className="form-group">
                    <input className="form-control" onChange={(e) => setTypedMessage(e.target.value)}/>
                    <button onClick={handleSendMessage}>
                        <i className="fas fa-paper-plane"/>
                    </button>
                </div>
                <div className="">
                    {handleDisplayMessage()}
                </div>
            </div>
            <div className="col-6">
                <Board/>
            </div>
            <div className="col-3 border border-danger">Alo</div>
            <SockJsClient url={ApiConstants.SOCKET_CONNECT_URL}
                          topics={['/room/' + roomId]}
                          onConnect={() => {
                              console.log("connected");
                          }}
                          onDisconnect={() => {
                              console.log("Disconnected");
                          }}
                          onMessage={(msg) => {
                              console.log("receive", msg);
                              const jobs = messages;
                              jobs.push(msg);
                              console.log("jobs", jobs);
                              setMessages(jobs);
                          }}
                          ref={clientRef}/>
        </div>
    );

}