import React, { useEffect, useState } from "react";
import axios from "axios";
import GifCard from "../components/GifCard";

const GifList = ({ searchString }) => {
    const [isLoaded, setLoaded] = useState(false);
    const [data, setData] = useState([]);
    const [error, setError] = useState(undefined);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await axios.get(`http://localhost:8080/api/search?q=${searchString}`, { withCredentials: true });
                console.log(res.data);
                setData(res.data);
                setLoaded(true);
            } catch (error) {
                setData([]);
                setLoaded(true);
                setError(error.message);
            }
        };

        fetchData();
    }, [searchString]);


    function renderGifCard(key, liked, url) {
        return (
            <GifCard key={key} liked={liked} gifId={key} imgSrcUrl={url}/>
        );
    }

    if (error) {
        return <div>Error: {error}</div>;
    }


    if (!isLoaded) {
        return <div>Loading...</div>;
    }

    const gifs = data
        .map(d => renderGifCard(d.giphyId, d.liked, d.url));

    return (
        <div className="App">
            {gifs}
        </div>
    );
};

export default GifList;