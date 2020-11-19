import React from "react";
import {Switch, Route, Redirect} from "react-router-dom";
import NotFound from "./common/NotFound";
import Login from "./login/Login";
import Home from "./home/Home";
import Game from "./game/Game";
import PrivateRoute from "./common/PrivateRoute";
import Signup from "./signup/Signup";
import axios from 'axios';
import {API_BASE_URL} from "./common/constants";
import LoadingIndicator from "./common/LoadingIndicator";
import OAuth2RedirectHandler from "./common/OAuth2RedirectHandler";

export default function App(props) {

    const [isAuthenticated, setAuthenticated] = React.useState(false);
    const [currentUser, setCurrentUser] = React.useState(null);
    const [isLoading, setLoading] = React.useState(true);

    if (!isAuthenticated) {
        axios.get(API_BASE_URL + '/api/auth/validate', {withCredentials: true})
            .then(res => {
                console.log(res);

                setAuthenticated(true);
                setLoading(false);
            })
            .catch(error => {
                console.log(error);
                setLoading(false);
            })
    }

    if (isLoading) return <LoadingIndicator/>;

    return (
        <Switch>
            <Route exact path="/home" component={Home}>
            </Route>

            <Route exact path="/">
                <Redirect to="/home"/>
            </Route>

            <Route exact path="/login"
                   render={(props) => <Login authenticated={isAuthenticated} {...props} />}/>

            <Route exact path="/signup" component={Signup}/>

            <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}/>

            <PrivateRoute path="/game" isAuthenticated={isAuthenticated} component={Game}/>

            <Route path="/">
                <NotFound/>
            </Route>
        </Switch>
    );
}
