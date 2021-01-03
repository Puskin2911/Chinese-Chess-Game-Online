import React from "react";

export default class Clock extends React.Component {
    constructor(props) {
        super(props);

        this.stompClient = props.stompClient;
        this.subscription = null;

        this.state = {
            isMyTurn: false,
            timeLeft: 15 * 60 * 1000
        }
    }

    handleTimeChange = () => {
        const timeLeft = this.state.timeLeft;

        if (timeLeft === 0) {
            this.stompClient.send("/app/room/" + this.props.room.id + "/game/time-over", {}, {});
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
        this.subscription = this.stompClient.subscribe("/room/" + this.props.room.id + "/move", (payload) => {
            const gameDto = JSON.parse(payload.body);
            const isMyTurnToUpdate = gameDto.nextTurnUsername === this.props.user.username;

            this.setState({
                isMyTurn: isMyTurnToUpdate
            });
        });
    }

    componentWillUnmount() {
        if (this.subscription) {
            this.subscription.unsubscribe();
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