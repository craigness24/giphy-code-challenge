import React, {useState} from "react";
import GifGrid from "./GifGrid"
import SearchBar from "../SearchBar";

function Home(props) {
    const [searchString, setSearchString] = useState("");

    return (
        <div>
            <SearchBar onClick={(text) => setSearchString(text)}/>
            <GifGrid searchString={searchString}/>
        </div>
    )
}

export default Home;