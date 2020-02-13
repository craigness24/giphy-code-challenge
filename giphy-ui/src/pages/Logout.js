import React, { useEffect, useState } from "react";
import { Redirect } from 'react-router-dom';
import axios from "axios";

const Logout = () => {
    const [isLoggedOut, setLoggedOut] = useState(false);

    useEffect(() => {
        axios.get("http://localhost:8080/logout", { withCredentials: true })
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