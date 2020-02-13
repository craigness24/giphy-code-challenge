import React, {useState} from "react";
import GifList from "./GifList"
import SearchBar from "../SearchBar";
import MenuBar from "../components/MenuBar";

function Home(props) {
    const [searchString, setSearchString] = useState("");

    return (
        <div>

            <SearchBar onClick={(text) => setSearchString(text)}/>
            <GifList searchString={searchString}/>
        </div>
    )
}

export default Home;