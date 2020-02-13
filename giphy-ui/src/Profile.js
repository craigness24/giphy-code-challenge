import React, { useEffect, useState } from "react";
import axios from "axios";

const Profile = () => {
    const [profile, setProfile] = useState({});

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await axios.get("http://localhost:8080/user", { withCredentials: true });
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