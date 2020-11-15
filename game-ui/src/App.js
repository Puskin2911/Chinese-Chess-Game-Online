import React from "react";
import {Switch, Route, Redirect} from "react-router-dom";
import NotFound from "./components/Error/NotFound/NotFound";
import Login from "./components/Login/Login";
import Home from "./components/Home/Home";
import Game from "./components/Game/Game";
import PrivateRoute from "./router/PrivateRoute";
import Signup from "./components/Signup/Signup";

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
