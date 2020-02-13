import React, { useEffect, useState } from "react";
import HTTP from "../config/app-config"
import GifList from "./GifList";

const Profile = () => {
    const [appGiphys, setAppGiphys] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                console.log("here");
                const res = await HTTP.get("/api/giphys");
                setAppGiphys(res.data)
            } catch (err) {
                console.log(err);
            }
        };

        fetchData();
    }, []);

    return (
        <div>
            <GifList gifList={appGiphys}/>
        </div>
    );
};

export default Profile;
