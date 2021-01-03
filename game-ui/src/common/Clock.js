import React from "react";

export default class Clock extends React.Component {
    constructor(props) {
        super(props);

        this.stompClient = props.stompClient;
        this.subscriptions = [];

        this.state = {
            isMyTurn: false,
            timeLeft: 15 * 60 * 1000
        }
    }

    handleTimeChange = () => {
        const timeLeft = this.state.timeLeft;

        if (timeLeft === 0) {
            const msgToSent = {
                username: this.props.user.username
            };
            this.stompClient.send("/app/room/" + this.props.room.id + "/game/time-over", {}, JSON.stringify(msgToSent));
            return "00:00";
        }

        if (this.state.isMyTurn) {
            setTimeout(() => {
                this.setState({
                    timeLeft: timeLeft - 1000
                });
            }, 1000)
        }

        const timeInSecond = timeLeft / 1000;
        const minutes = parseInt(timeInSecond / 60 + "", 10);
        let second = timeInSecond % 60;
        if (second === 0) second = "00";
        return minutes + ":" + second;
    }

    componentDidMount() {
        const roomId = this.props.room.id;

        const moveSub = this.stompClient.subscribe("/room/" + roomId + "/move", (payload) => {
            const gameDto = JSON.parse(payload.body);
            const isMyTurnToUpdate = gameDto.nextTurnUsername === this.props.user.username;

            this.setState({
                isMyTurn: isMyTurnToUpdate
            });
        });

        const startSub = this.stompClient.subscribe("/room/" + roomId + "/game/start", (payload) => {
            console.log("From Room receive signal start game!");

            this.setState({
                timeLeft: 15 * 60 * 1000
            });
        });

        this.subscriptions.push(moveSub, startSub);
    }

    componentWillUnmount() {
        for (let i = 0; i < this.subscriptions.length; i++) {
            this.subscriptions[i].unsubscribe();
        }
    }

    render() {
        if (!this.props.isEnable) return null;
        return (
            <div className="my-2">
                <div className="text-center">
                    <span className="p-1 bg-white rounded">
                        <code>
                            Time: {this.handleTimeChange()}
                        </code>
                    </span>
                </div>
            </div>
        );
    }

}