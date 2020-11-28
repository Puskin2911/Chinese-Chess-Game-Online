import {Link, useLocation} from "react-router-dom";
import React from "react";

function NotFound() {
    let location = useLocation();

    return (
        <div>
            <h3>
                No match for <code>{location.pathname}</code>.
                Go to <Link to="/">Home</Link>
            </h3>
        </div>
    );
}

export default NotFound;