import React from "react";
import GifCard from "../components/GifCard";

const GifList = ({ gifList }) => {
    function renderGifCard(key, liked, url) {
        return (
            <GifCard key={key} liked={liked} gifId={key} imgSrcUrl={url}/>
        );
    }

    const gifCards = gifList
        .map(d => renderGifCard(d.giphyId, d.liked, d.url));

    return (
        <div className="App">
            {gifCards}
        </div>
    );
};

export default GifList;
