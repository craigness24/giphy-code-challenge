import React, { createContext, useEffect, useState } from "react";
import axios from "axios"

const context = createContext(null);

const UserProvider = ({ children }) => {
    const [user, setUser] = useState();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await axios.get("http://localhost:8080/me", { withCredentials: true });
                setUser(res.data)
            } catch (err) {
                console.log(err);
            }
        };

        fetchData();
    }, []);

    return (
        <context.Provider value={user}>
            {children}
        </context.Provider>
    );
};

UserProvider.context = context;

export default UserProvider;