import React from "react";
import GifCard from "../components/GifCard";

const GifList = ({ gifList }) => {
    function renderGifCard(appGiphy) {
        return (
            <GifCard key={appGiphy.giphyId}
                     categories={appGiphy.categories}
                     liked={appGiphy.liked}
                     gifId={appGiphy.giphyId}
                     imgSrcUrl={appGiphy.url}/>
        );
    }

    const gifCards = gifList
        .map(d => renderGifCard(d));

    return (
        <div>
            {gifCards}
        </div>
    );
};

export default GifList;
