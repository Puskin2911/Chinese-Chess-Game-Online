import React from "react";
import {Switch, Route, Redirect} from "react-router-dom";
import NotFound from "./components/Error/NotFound";
import Login from "./components/login/Login";
import Game from "./components/Game";
import PrivateRoute from "./common/PrivateRoute";
import Signup from "./components/Signup";
import OAuth2RedirectHandler from "./common/OAuth2RedirectHandler";

export default function App() {
    return (
        <Switch>
            <Route exact path="/">
                <Redirect to="/game"/>
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
