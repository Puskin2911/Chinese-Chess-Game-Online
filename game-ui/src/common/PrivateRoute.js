import React from "react";
import {Redirect, Route} from "react-router-dom";
import axios from "axios";
import {CHECK_AUTH_URL} from "./constants";

const PrivateRoute = ({component: Component, ...rest}) => {

    const [isAuthenticated, setAuthenticated] = React.useState(false);
    const [isLoading, setLoading] = React.useState(true);

    // Run only one after init render.
    React.useEffect(() => {
        axios.get(
            CHECK_AUTH_URL,
            {
                withCredentials: true
            }
        ).then(res => {
            console.log(res);
            setAuthenticated(true);
        }).catch((error) => {
            console.log(error.response);
        }).finally(() => {
            setLoading(false);
        });
    }, []);

    if (isLoading) return (
        <h2 className="text-center text-danger">Processing...</h2>
    );

    return (
        <Route
            {...rest}
            render={props =>
                isAuthenticated ?
                    <Component {...rest} {...props} />
                    :
                    <Redirect
                        to={{
                            pathname: '/login',
                            state: {from: props.location}
                        }}
                    />
            }
        />
    );
};

export default PrivateRoute;