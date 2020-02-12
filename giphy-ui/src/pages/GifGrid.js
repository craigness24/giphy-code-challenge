import React, {useEffect, useState} from "react";
import axios from "axios";
import {useAuth} from "../context/auth";

function GifGrid(props) {
    const [isLoaded, setLoaded] = useState(false);
    const [data, setData] = useState([]);
    const [error, setError] = useState(undefined);
    const {authCreds} = useAuth();

    useEffect(() => {
        console.log("on effect");
        axios.get(`http://localhost:8080/api/search?q=${props.searchString}`, {withCredentials: true})
            .then((res) => {
                setData(res.data.data);
                setLoaded(true);
            }, (rejected) => {
                setData([]);
                setLoaded(true);
                setError(rejected.message);
            })
            .catch((error) => {
                setData([]);
                setLoaded(true);
                setError(error.message);
            });
    }, [props.searchString, authCreds]);


    function renderSquare(key, url) {
        return (
            <img key={key} src={url} alt="none"/>
        );
    }

    if (error) {
        return <div>Error: {error}</div>;
    }


    if (!isLoaded) {
        return <div>Loading...</div>;
    }

    const mp4s = data
        .map(d => renderSquare(d.id, d.images.fixed_height.url));

    return (
        <div className="App">
            {mp4s}
        </div>
    );
}

export default GifGrid;