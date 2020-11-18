import React from "react";
import {Redirect, Route} from "react-router-dom";
import axios from "axios";

export default function PrivateRoute({children, ...rest}) {
    const [content, setContent] = React.useState(<h1>Processing</h1>);
    return (
        <Route {...rest} render={() => {
            axios.get(
                "http://127.0.0.1:8080/api/auth/validate",
                {withCredentials: true}
            ).then(res => {
                if (res.status === 200) setContent(children);
            }).catch(() => {
                setContent(<Redirect to={{
                    pathname: "/login",
                }}/>);
            });

            console.log("Before render...")
            return content;
        }}/>

    );
}