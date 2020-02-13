import React, { useEffect, useState } from "react";
import { Redirect } from 'react-router-dom';
import HTTP from "../config/app-config"

const Logout = () => {
    const [isLoggedOut, setLoggedOut] = useState(false);

    useEffect(() => {
        HTTP.get("/logout")
            .then(result => {
                setLoggedOut(true);
            })
            .catch(e => {
                console.log(e);
            });
    }, []);

    if (isLoggedOut) return <Redirect to={"/login"}/>;

    return <div>Logging out...</div>
};

export default Logout;
