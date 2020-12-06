import React from "react";
import {Link} from "react-router-dom";
import axios from "axios";
import ApiConstants from "../constants/ApiConstant";

export default function Signup() {
    document.title = "Chinese Chess | Signup";

    const [username, setUsername] = React.useState("");
    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [rePassword, setRePassword] = React.useState("");
    const [isCorrectPassword, setCorrectPassword] = React.useState(true);
    const [error, setError] = React.useState("");

    const [success, setSuccess] = React.useState(false);

    React.useEffect(() => {
            if (password === rePassword) {
                setCorrectPassword(true);
                setError("");
            } else {
                setCorrectPassword(false);
                setError("Password didn't match");
            }
        }, [password, rePassword]
    );

    const handleSignup = (event) => {
        event.preventDefault();
        const userInfo = {
            username: username,
            email: email,
            password: password
        };

        if (!isCorrectPassword) return;

        axios.post(
            ApiConstants.SIGNUP_URL,
            userInfo
        ).then(res => {
            console.log(res);
            if (res.status === 201) {
                setSuccess(true);
            } else {
                setSuccess(false);
                setError("Something failed! Please Try again");
            }
        }).catch((error) => {
            console.log(error.response);
            if (error.response !== undefined) {
                if (error.response.status === 409) {
                    setSuccess(false);
                    setError("Username or Email has already exists!");
                }
            } else {
                setSuccess(false);
                setError("Something failed! Please Try again");
            }
        })

        setError("Processing ...");
    }

    if (success === true) {
        return (
            <div className="container bg-white rounded w-40 p-3 mt-5">
                <h4 className="text-center text-success">Successful</h4>
                <p className="text-center">Go to <Link to="/login">Login</Link></p>
            </div>
        );
    }

    return (
        <div className="container bg-white rounded w-40 p-3 mt-5">
            <h2 className="text-center">Sign up</h2>
            {error === "" ? "" : (<p className="text-danger text-center">{error}</p>)}
            <form className="mx-5">
                <div className='form-group'>
                    <input className="form-control" type="text" placeholder="Username" required
                           onChange={event => setUsername(event.target.value)}/>
                </div>
                <div className='form-group'>
                    <input className="form-control" type="email" placeholder="Email" required
                           onChange={event => setEmail(event.target.value)}/>
                </div>
                <div className='form-group'>
                    <input className="form-control" type="password" placeholder="Password" required
                           onChange={event => setPassword(event.target.value)}/>
                </div>
                <div className='form-group'>
                    <input className="form-control" type="password" placeholder="Confirm password" required
                           onChange={event => setRePassword(event.target.value)}/>
                </div>
                <div className='form-group text-center'>
                    <button type="submit" className="form-control btn btn-success w-50" onClick={handleSignup}>Sign up
                    </button>
                </div>

                <p className="text-center">Or go to <Link to="/login">Login</Link></p>

                <hr className="my-4"/>
                <h6 className="text-center">Sign up with</h6>
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
                            <button type="button" className="form-control btn btn-danger">
                                <i className="fab fa-google-plus-g"/>
                                <span className="pl-2">Google</span>
                            </button>
                        </div>
                    </div>
                </div>
                <div className="row justify-content-around">
                    <div className="col-6">
                        <div className="form-group">
                            <button type="button" className="form-control btn btn-secondary">
                                <i className="fab fa-github"/>
                                <span className="pl-2">GitHub</span>
                            </button>
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
    );
}