import React from "react";
import {Link, useHistory, useLocation} from "react-router-dom";
import {GITHUB_AUTH_URL, GOOGLE_AUTH_URL, LOGIN_URL} from "../../common/constants";
import axios from "axios";

export default function Login() {
    document.title = "Chinese Chess | Login";

    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [notification, setNotification] = React.useState("");

    let history = useHistory();
    let location = useLocation();
    let {from} = location.state || {from: {pathname: "/"}};

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
        <div className="container bg-white rounded w-40 p-3 mt-5" id="login-box">
            <h2 className="text-center mb-4">Login@@</h2>
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

                <hr className="my-4"/>
                <h6 className="text-center">Login with</h6>
                <div className="row justify-content-around mt-4">
                    <div className="col-6">
                        <div className="form-group">
                            <button type="button" className="form-control btn btn-primary">
                                <i className="fab fa-facebook"/>
                                <span className="pl-2">Facebook</span>
                            </button>
                        </div>
                    </div>
                    <div className="col-6">
                        <div className="form-group">
                            <a href={GOOGLE_AUTH_URL} target="_blank" rel="noreferrer"
                               className="form-control btn btn-danger">
                                <i className="fab fa-google-plus-g"/>
                                <span className="pl-2">Google</span>
                            </a>
                        </div>
                    </div>
                </div>
                <div className="row justify-content-around">
                    <div className="col-6">
                        <div className="form-group">
                            <a href={GITHUB_AUTH_URL} className="form-control btn btn-secondary">
                                <i className="fab fa-github"/>
                                <span className="pl-2">GitHub</span>
                            </a>
                        </div>
                    </div>
                    <div className="col-6">
                        <div className="form-group">
                            <button type="button" className="form-control btn btn-dark" data-toggle="modal"
                                    data-target="#pornhub-modal">
                                <i className="fas fa-heart"/>
                                <span className="pl-2">PornHub</span>
                            </button>
                            <div className="modal fade" id="pornhub-modal" tabIndex="-1" role="dialog"
                                 aria-labelledby="pornhub-title" aria-hidden="true">
                                <div className="modal-dialog" role="document">
                                    <div className="modal-content">
                                        <div className="modal-header">
                                            <h5 className="modal-title text-center text-danger" id="pornhub-title">Stop
                                                Stop Stop !!!</h5>
                                            <button type="button" className="close" data-dismiss="modal"
                                                    aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div className="modal-body">
                                            <b>Bắt được có tài khoản xem phim heo nha!</b>
                                            <p>Đăng nhập bằng chức năng khác đi!</p>
                                        </div>
                                        <div className="modal-footer">
                                            <button type="button" className="btn btn-secondary"
                                                    data-dismiss="modal">Close
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    )
}