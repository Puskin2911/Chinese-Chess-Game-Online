import React from "react";
import {Switch, Route, Redirect} from "react-router-dom";
import NotFound from "./common/NotFound";
import Login from "./login/Login";
import Home from "./home/Home";
import Game from "./game/Game";
import PrivateRoute from "./common/PrivateRoute";
import Signup from "./signup/Signup";

export default function App() {
    return (
        <Switch>
            <Route exact path="/home" component={Home}>
            </Route>

            <Route exact path="/">
                <Redirect to="/home"/>
            </Route>

            <Route exact path="/login">
                <Login/>
            </Route>

            <Route exact path="/signup">
                <Signup/>
            </Route>

            <PrivateRoute path="/game">
                <Game/>
            </PrivateRoute>

            <Route path="/">
                <NotFound/>
            </Route>
        </Switch>
    );
}
