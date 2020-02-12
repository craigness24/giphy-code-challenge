import React, {useState} from 'react';
import {BrowserRouter as Router, Route} from "react-router-dom";
import './App.css';
import Home from "./pages/Home";
import Admin from "./Admin";
import {AuthContext} from "./context/auth";
import PrivateRoute from "./PrivateRoute";
import Login from "./pages/Login";
import Signup from "./pages/SignUp";

function App(props) {
    const [authCreds, setAuthCreds] = useState({});

    return (
        <AuthContext.Provider value={{authCreds, setAuthCreds}}>
            <Router>
                <div>
                    <Route exact path="/" component={Home}/>
                    <Route path="/login" component={Login}/>
                    <Route path="/signup" component={Signup}/>
                    <PrivateRoute path="/admin" component={Admin}/>
                </div>
            </Router>
        </AuthContext.Provider>
    );

}

export default App;
