import React from "react";
import { Nav, Navbar } from "react-bootstrap";

const MenuBar = () => {
    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Navbar.Brand href="#home">Giphy Proxy</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav"/>
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link href="/">Home</Nav.Link>
                    <Nav.Link href="/login">Login</Nav.Link>
                    <Nav.Link href="/logout">Logout</Nav.Link>
                    <Nav.Link href="/register">Register</Nav.Link>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    );
};

export default MenuBar