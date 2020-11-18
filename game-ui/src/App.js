import React from "react";
import {Switch, Route, Redirect} from "react-router-dom";
import NotFound from "./common/NotFound";
import Login from "./Login/Login";
import Home from "./Home/Home";
import Game from "./Game/Game";
import PrivateRoute from "./common/PrivateRoute";
import Signup from "./Signup/Signup";

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
