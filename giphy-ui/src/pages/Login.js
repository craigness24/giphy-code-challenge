import React, { useState } from "react";
import { Link, Redirect } from 'react-router-dom';
import axios from "axios";

const Login = (props) => {
    const [isLoggedIn, setLoggedIn] = useState(false);
    const [isError, setIsError] = useState(false);
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");

    const referer = props.location.state?.referer || '/';

    function loginClick() {
        const authConfig = {
            auth: {
                username: userName,
                password: password
            },
            withCredentials: true
        };

        axios.get("http://localhost:8080/login", authConfig)
            .then(result => {
                if (result.status === 200) {
                    setLoggedIn(true);
                } else {
                    setIsError(true);
                }
            })
            .catch(e => {
                setIsError(true);
            });
    }

    if (isLoggedIn) {
        return <Redirect to={referer}/>;
    }

    return (
        <div>
            <div>
                <input
                    type="username"
                    value={userName}
                    onChange={e => {
                        setUserName(e.target.value);
                    }}
                    placeholder="email"
                />
                <input
                    type="password"
                    value={password}
                    onChange={e => {
                        setPassword(e.target.value);
                    }}
                    placeholder="password"
                />
                <button onClick={loginClick}>Sign In</button>
            </div>
            <Link to="/signup">Don't have an account?</Link>
            {isError && <div>The username or password provided were incorrect!</div>}
        </div>
    );
};

export default Login;