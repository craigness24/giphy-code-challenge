import React, { useEffect, useState } from "react";
import HTTP from "../config/app-config"

const Profile = () => {
    const [profile, setProfile] = useState({});

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await HTTP.get("/profile");
                setProfile(res.data)
            } catch (err) {
                console.log(err);
            }
        };

        fetchData();
    }, []);

    return (
        <div>
            <div>Profile!</div>
        </div>
    );
};

export default Profile;
