import './App.css';
import React from "react";
import {Switch, Route, Redirect, useLocation, Link} from "react-router-dom";
import NotFound from "./components/Error/NotFound/NotFound";
import axios from 'axios';

function App() {
    return (
        <div>
            <Switch>
                <Route exact path="/home">
                    <Home/>
                </Route>

                <Route exact path="/">
                    <Redirect to="/home"/>
                </Route>

                <Route exact path="/login">
                    <Login/>
                </Route>

                <PrivateRoute path="/game">
                    <Game/>
                </PrivateRoute>

                <Route path="/">
                    <NotFound/>
                </Route>
            </Switch>
        </div>
    );
}

function Home() {
    return <h2>Home</h2>;
}

const fakeAuth = {
    isAuthenticated: false,
    authenticate(callback) {
        this.isAuthenticated = true;
        setTimeout(callback, 100) // fake async
    },
    signOut(callback) {
        this.isAuthenticated = false;
        setTimeout(callback, 100) // fake async
    }
}

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            redirectToReferrer: false
        };
    }

    componentDidMount() {

    }

    handleUsernameChange = () => {
        this.setState({username: document.getElementById('username').value});
    }

    handlePasswordChange = () => {
        this.setState({password: document.getElementById('password').value});
    }


    handleLogin = (e) => {
        e.preventDefault();

        const userInfo = {
            username: this.state.username,
            password: this.state.password
        };

        axios.post("http://localhost:8080/api/auth/login", userInfo)
            .then(res => {
                console.log(res);
            })

        fakeAuth.authenticate(() => {
            this.setState(() => ({
                // redirectToReferrer: true
            }));
        })
    }

    render() {
        if (this.state.redirectToReferrer === true) {
            return <Redirect to="/game"/>
        }

        return (
            <div>
                <p>You must log in to view the page</p>
                <form id="login-from">
                    <input id="username" type="text" onChange={this.handleUsernameChange} placeholder="username"/>
                    <input id="password" type="password" onChange={this.handlePasswordChange} placeholder="password"/>
                    <button onClick={this.handleLogin}>Log in</button>
                </form>
            </div>
        )
    }
}

function Game() {
    return <h3>Games</h3>;
}


function PrivateRoute({children, ...rest}) {
    return (
        <Route {...rest} render={(props) => (
            fakeAuth.isAuthenticated === true
                ? children
                : <Redirect to='/login'/>
        )}/>
    );
}

export default App;
