import React, { useState } from "react";
import { Link, Redirect } from 'react-router-dom';
import { Button, Form } from "react-bootstrap";
import HTTP from "../config/app-config"

const Registration = () => {
    const [registered, setRegistered] = useState(false);
    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [passwordCheck, setPasswordCheck] = useState('');

    const onRegister = () => {
        HTTP.post("/register",
            {
                userName: userName,
                password: password,
                passwordCheck: passwordCheck
            })
            .then(res => {
                setRegistered(true);
            })
            .catch(error => {
                console.log(error);
            });
    };

    if (registered) {
        return <Redirect to={"/login"}/>;
    }

    return (
        <div>
            <Form>
                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Username</Form.Label>
                    <Form.Control type="text" placeholder="Enter email" onChange={e => {
                        setUserName(e.target.value);
                    }}/>
                    <Form.Text className="text-muted">
                        We'll never share your username with anyone else.
                    </Form.Text>
                </Form.Group>
                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" onChange={e => {
                        setPassword(e.target.value);
                    }}/>
                </Form.Group>
                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Password Again</Form.Label>
                    <Form.Control type="password" placeholder="Password Check" onChange={e => {
                        setPasswordCheck(e.target.value);
                    }}/>
                </Form.Group>
                <Button variant="primary" type="submit" onClick={onRegister}>
                    Submit
                </Button>
            </Form>
            <Link to="/login">Already have an account?</Link>
        </div>
    );
};

export default Registration;
