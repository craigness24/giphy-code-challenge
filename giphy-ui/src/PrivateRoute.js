import React, { useContext } from 'react';
import {Redirect, Route} from 'react-router-dom';
import UserProvider from "./context/UserProvider";

function PrivateRoute({ component: Component, ...rest }) {
    const userData = useContext(UserProvider.context);
    console.log("userData")
    console.log(userData);
    console.log(userData ? "true" : "false");
    return (
        <Route
            {...rest}
            render={props =>
                userData ? (
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