import React from "react";
import {Link, useHistory, useLocation} from "react-router-dom";
import localStorageHelper from "../../utils/LocalStorageHelper";
import authService from "../../services/AuthService";
import Spinner from "../../common/Spinner";

export default function NormalLoginForm() {

    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [notification, setNotification] = React.useState("");
    const [isLoading, setLoading] = React.useState(false);

    const history = useHistory();
    const location = useLocation();
    const {from} = location.state || {from: {pathname: "/"}};


    const handleLogin = (event) => {
        event.preventDefault();

        setLoading(true);
        setNotification("");

        const userInfo = {
            username: username,
            password: password
        };

        authService.login(userInfo)
            .then(res => {
                console.log(res);

                localStorageHelper.setCookie("loggedIn", true, 10, true);
                history.replace(from);
            })
            .catch((error) => {
                setLoading(false);

                if (error.response !== undefined) {
                    setNotification(error.response.data.details);
                }
            });
    }

    return (
        <div>
            <Spinner isHiding={!isLoading}/>
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