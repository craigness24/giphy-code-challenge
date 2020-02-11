import React, {useState} from "react";
import {Link, Redirect} from 'react-router-dom';
import {useAuth} from "../context/auth";
import axios from "axios";

function Login(props) {
    const [isLoggedIn, setLoggedIn] = useState(false);
    const [isError, setIsError] = useState(false);
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const {setAuthTokens} = useAuth();

    const referer = props.location.state?.referer || '/';

    function loginClick() {
        // const encoded = window.btoa(`${userName}:${password}`);
        axios.get("http://localhost:8080/login", {
            auth: {
                username: userName,
                password: password
            }
            // headers: {
            //     Authorization: `Basic ${encoded}`
            // }
        }).then(result => {
            if (result.status === 200) {
                setAuthTokens(result.data);
                setLoggedIn(true);
            } else {
                setIsError(true);
            }
        }).catch(e => {
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
}

export default Login;