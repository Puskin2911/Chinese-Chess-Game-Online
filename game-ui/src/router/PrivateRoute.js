import React from "react";
import {useAuth} from "../provider/AuthProvider";
import {Redirect, Route} from "react-router-dom";

export default function PrivateRoute({children, ...rest}) {
    let auth = useAuth();
    return (
        <Route {...rest} render={({props}) => (
            auth.isAuthenticated
                ? children
                : <Redirect to={{
                    pathname: "/login",
                }}/>
        )}/>
    );
}