import React from "react";
import {Switch, Route, Redirect} from "react-router-dom";
import NotFound from "./components/NotFound";
import Login from "./components/login/Login";
import Home from "./components/Home";
import Game from "./components/Game";
import PrivateRoute from "./common/PrivateRoute";
import Signup from "./components/Signup";
import OAuth2RedirectHandler from "./common/OAuth2RedirectHandler";

export default function App() {
    return (
        <Switch>
            <Route exact path="/home" component={Home}>
            </Route>

            <Route exact path="/">
                <Redirect to="/home"/>
            </Route>

            <Route exact path="/login" component={Login}/>

            <Route exact path="/signup" component={Signup}/>

            <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}/>

            <PrivateRoute path="/game" component={Game}/>

            <Route path="/">
                <NotFound/>
            </Route>
        </Switch>
    );
}
