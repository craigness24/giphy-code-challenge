import React from 'react';
import { Route, Router } from "react-router-dom";
import './App.css';
import Home from "./pages/Home";
import Profile from "./pages/Profile";
import Login from "./pages/Login";
import Registration from "./pages/Registration";
import history from "./history";
import MenuBar from "./components/MenuBar";
import { Container } from "react-bootstrap";
import Logout from "./pages/Logout";

const App = (props) => {
    return (
        <div>
            <MenuBar/>
            <Container>
                <Router history={history}>
                    {/*<UserProvider>*/}
                    <Route exact path="/" component={Home}/>
                    <Route path="/login" component={Login}/>
                    <Route path="/register" component={Registration}/>
                    <Route path="/logout" component={Logout}/>
                    <Route path="/profile" component={Profile}/>
                    {/*<PrivateRoute path="/profile" component={Profile}/>*/}
                    {/*</UserProvider>*/}
                </Router>
            </Container>
        </div>
    );
};

export default App;
