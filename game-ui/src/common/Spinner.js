import React from "react";

export default function Spinner(props) {
    const isHiding = props.isHiding;

    if (isHiding) return <div/>;

    else return (
        <div className="d-flex align-items-center justify-content-center">
            <div className="spinner-border text-primary text-center" role="status">
                <span className="sr-only">Loading...</span>
            </div>
        </div>
    );
}