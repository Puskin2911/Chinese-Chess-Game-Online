import React from "react";
import NormalLoginForm from "./NormalLoginForm";
import SocialLoginForm from "./SocialLoginForm";
import axios from "axios";
import {CHECK_AUTH_URL} from "../../common/constants";
import {useHistory, useLocation} from "react-router-dom";
import LoadingIndicator from "../../common/LoadingIndicator";

export default function Login() {
    document.title = "Chinese Chess | Login";

    const history = useHistory();
    const location = useLocation();
    const {from} = location.state || {from: {pathname: "/"}};

    if (from.pathname === '/') {
        axios.get(
            CHECK_AUTH_URL,
            {
                withCredentials: true
            }
        ).then(res => {
            console.log(res);
            history.replace("game");
        }).catch((error) => {
            console.log(error.response);
        });

        return <LoadingIndicator/>
    }

    return (
        <div className="container bg-white rounded w-40 p-3 mt-5" id="login-box">
            <h2 className="text-center mb-4">Login</h2>
            <NormalLoginForm from={from} history={history}/>
            <hr className="my-4 mx-5"/>
            <SocialLoginForm/>
        </div>
    )
}