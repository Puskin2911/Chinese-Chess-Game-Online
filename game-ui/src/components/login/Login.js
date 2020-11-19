import React from "react";
import NormalLoginForm from "./NormalLoginForm";
import SocialLoginForm from "./SocialLoginForm";

export default function Login() {
    document.title = "Chinese Chess | Login";

    return (
        <div className="container bg-white rounded w-40 p-3 mt-5" id="login-box">
            <h2 className="text-center mb-4">Login</h2>
            <NormalLoginForm/>
            <hr className="my-4 mx-5"/>
            <SocialLoginForm/>
        </div>
    )
}