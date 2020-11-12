import React from "react";
import {Redirect} from "react-router-dom";
import axios from "axios";

export default function Login() {
    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [isLoggedIn, setLoggedIn] = React.useState(false);
    const [isError, setIsError] = React.useState(false);

    const auth = useAuth();

    const handleLogin = (event) => {
        event.preventDefault();
        console.log(auth);

        const userInfo = {
            username: username,
            password: password
        };

        axios.post(
            "http://127.0.0.1:8080/api/auth/login",
            userInfo,
            {
                withCredentials: true
            }
        ).then(res => {
            console.log(res);
            if (res.status === 200) {
                auth.setAuthenticated(true);
                setLoggedIn(true);

            } else {
                setIsError(true);
            }
        }).catch(() => {
            setIsError(true);
        })
    }

    console.log("Check loggedIn");
    if (isLoggedIn === true) {
        return <Redirect to="/game"/>
    }

    return (
        <div>
            <form id="login-from">
                <input id="username" type="text" onChange={event => setUsername(event.target.value)}
                       placeholder="username"/>
                <input id="password" type="password" onChange={event => setPassword(event.target.value)}
                       placeholder="password"/>
                <button onClick={handleLogin}>Log in</button>
            </form>
        </div>
    )
}