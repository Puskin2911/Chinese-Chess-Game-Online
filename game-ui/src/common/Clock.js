import React from "react";

export default class Clock extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        if (!this.props.isEnable) return null;
        return (
            <div className="my-2">
                <div className="text-center">
                    <span className="p-1 bg-white rounded">
                        <code>
                            Time: 00:00
                        </code>
                    </span>
                </div>
            </div>
        );
    }

}