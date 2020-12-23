import {Link, useLocation} from "react-router-dom";
import React from "react";

function NotFound() {
    let location = useLocation();

    return (
        <div className="container mt-5 p-5 bg-white rounded">
            <h3 className="text-center">
                Hey hey! Where do you want to go ? You will find nothing in <code>{location.pathname}</code>.
            </h3>
            <h4 className="text-center">Go <Link to="/">Home</Link> now.</h4>
        </div>
    );
}

export default NotFound;