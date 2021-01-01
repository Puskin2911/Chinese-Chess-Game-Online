import React from "react";
import {Switch, Route, Redirect} from "react-router-dom";
import NotFound from "./components/Error/NotFound";
import Login from "./components/login/Login";
import Home from "./components/Home";
import PrivateRoute from "./common/PrivateRoute";
import Signup from "./components/Signup";

export default function App() {
    return (
        <Switch>
            <Route exact path="/">
                <Redirect to="/game"/>
            </Route>

            <Route exact path="/login" component={Login}/>

            <Route exact path="/signup" component={Signup}/>

            <PrivateRoute path="/game" component={Home}/>

            <Route path="/">
                <NotFound/>
            </Route>
        </Switch>
    );
}
