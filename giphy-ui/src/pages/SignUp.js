import React from "react";
import {Link} from 'react-router-dom';

function Signup() {
    return (
        <div>
            <div>
                <input type="email" placeholder="email"/>
                <input type="password" placeholder="password"/>
                <input type="password" placeholder="password again"/>
                <button>Sign Up</button>
            </div>
            <Link to="/login">Already have an account?</Link>
        </div>
    );
}

export default Signup;