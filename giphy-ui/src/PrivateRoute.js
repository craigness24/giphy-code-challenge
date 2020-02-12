import React from 'react';
import {Redirect, Route} from 'react-router-dom';
import {useAuth} from "./context/auth";

function PrivateRoute({ component: Component, ...rest }) {
    const { authCreds } = useAuth();

    return (
        <Route
            {...rest}
            render={props =>
                authCreds ? (
                    <Component {...props} />
                ) : (
                    <Redirect
                        to={{ pathname: "/login", state: { referer: props.location } }}
                    />
                )
            }
        />
    );
}

export default PrivateRoute;