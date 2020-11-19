import {Link} from "react-router-dom";
import axios from "axios";
import {LOGIN_URL} from "../../common/constants";
import React from "react";

export default function NormalLoginForm(props) {

    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [notification, setNotification] = React.useState("");

    const history = props.history;
    const from = props.from;


    const handleLogin = (event) => {
        event.preventDefault();

        const userInfo = {
            username: username,
            password: password
        };

        axios.post(
            LOGIN_URL,
            userInfo,
            {
                withCredentials: true
            }
        ).then(res => {
            console.log(res);

            history.replace(from);
        }).catch((error) => {
            console.log(error.response);
            setNotification(error.response.data.details);
        })
    }

    return (
        <div>
            <h5 className="text-center text-danger">{notification}</h5>
            <form className="mx-5">
                <div className='form-group'>
                    <input className="form-control" type="text" placeholder="Username" required
                           onChange={event => {
                               setUsername(event.target.value);
                               setNotification("");
                           }}/>
                </div>
                <div className='form-group'>
                    <input className="form-control" type="password" placeholder="Password" required
                           onChange={event => {
                               setPassword(event.target.value);
                               setNotification("");
                           }}/>
                </div>
                <div className='form-group text-center'>
                    <button type="submit" className="form-control btn btn-success w-50" onClick={handleLogin}>Log in
                    </button>
                </div>

                <p className="text-center">Or go to <Link to="/signup">Sign up</Link></p>
            </form>
        </div>
    )
}