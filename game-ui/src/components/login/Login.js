import React from "react";
import NormalLoginForm from "./NormalLoginForm";
import SocialLoginForm from "./SocialLoginForm";
import {useHistory, useLocation} from "react-router-dom";
import LoadingIndicator from "../../common/LoadingIndicator";
import localStorageHelper from "../../utils/LocalStorageHelper";
import authService from "../../services/AuthService";

export default function Login() {
    document.title = "Chinese Chess Game | Login";

    const history = useHistory();
    const location = useLocation();
    const {from} = location.state || {from: {pathname: "/"}};
    const [isLoading, setLoading] = React.useState(true);

    if (from.pathname === '/' && isLoading) {
        const isLoggedIn = localStorageHelper.getCookie("loggedIn");
        if (!isLoggedIn) {
            setLoading(false);
        } else {
            authService.validateUser()
                .then(res => {
                    console.log("from login", res);
                    history.replace("game");
                })
                .catch((error) => {
                    console.log("from login", error.response);
                    setLoading(false);
                });
        }

        return <LoadingIndicator/>;
    }

    return (
        <div className="container vh-100">
            <div className="row justify-content-center">
                <div className="col-12 col-sm-10 col-md-8 col-lg-7 col-xl-6 bg-white rounded w-40 p-3 mt-5">
                    <h2 className="text-center mb-4">Login</h2>
                    <NormalLoginForm/>
                    <hr className="my-4 mx-5"/>
                    <SocialLoginForm/>
                </div>
            </div>
        </div>
    )
}