import React from "react";

export default function SendMessage(props) {

    const username = props.username;
    const room = props.room;
    const roomId = room.id;
    const stompClient = props.stompClient;

    const [typedMessage, setTypedMessage] = React.useState("");
    const [preparedText, setPreparedText] = React.useState("");


    const handleSendMessage = (event) => {
        event.preventDefault();
        if (typedMessage.length === 0) return;
        const msgToSend = {
            username: username,
            message: typedMessage
        }
        stompClient.send('/app/room/' + roomId + "/chat", {}, JSON.stringify(msgToSend));
        setTypedMessage("");
    };

    const handleChoosePreparedText = (event) => {
        setPreparedText(event.target.value);
        setTypedMessage(event.target.value);
    }

    return (
        <div className="">
            <form>
                <div className="input-group">
                    <input type="text" className="form-control"
                           onChange={(e) => setTypedMessage(e.target.value)}
                           value={typedMessage} placeholder="Chat không ?"/>
                    <div className="input-group-append">
                        <span className="input-group-text">
                            <button type="submit" className="btn border-0 p-0"
                                    onClick={handleSendMessage}>
                                <i className="fas fa-paper-plane"/>
                                </button>
                        </span>
                    </div>
                    <div className="input-group">
                        <select value={preparedText} onChange={handleChoosePreparedText}
                                className="custom-select">
                            <option value="">Chat nhanh ở đây</option>
                            <option value="Nước đó rất cao!">Nước đó rất cao!</option>
                            <option value="Làm ván nữa nhé!">Làm ván nữa nhé!</option>
                            <option value="Thua đi bác ơi!">Thua đi bác ơi!</option>
                            <option value="Các hạ quá khen!">Các hạ quá khen!</option>
                            <option value="Nhanh đi lâu vậy!">Nhanh đi lâu vậy!</option>
                            <option value="Kết bạn đi!">Kết bạn đi!</option>
                            <option value="Chơi hay nghỉ đây?">Chơi hay nghỉ đây?</option>
                            <option value="Nhường em bác ơi !!">Nhường em bác ơi !!</option>
                        </select>
                    </div>
                </div>
            </form>
        </div>
    );
}