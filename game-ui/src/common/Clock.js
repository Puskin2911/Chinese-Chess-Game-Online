import React from "react";

export default class Clock extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        if (!this.props.isEnable) return null;
        return (
            <div className="my-2">
                Clock here
            </div>
        );
    }

}