import React, { useEffect, useState } from "react";
import GifList from "./GifList"
import HTTP from "../config/app-config";
import { Col, Container, Row } from "react-bootstrap";
import SearchBar from "../components/SearchBar";

function Home(props) {
    const [searchString, setSearchString] = useState("");
    const [isLoaded, setLoaded] = useState(false);
    const [gifList, setGifList] = useState([]);
    const [error, setError] = useState(undefined);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await HTTP.get(`/api/search?q=${searchString}`);
                setGifList(res.data);
                setLoaded(true);
            } catch (error) {
                setGifList([]);
                setLoaded(true);
                setError(error.message);
            }
        };

        fetchData();
    }, [searchString]);

    const content = () => {
        if (error) {
            return <div>Error: {error}</div>;
        }

        if (!isLoaded) {
            return <div>Loading...</div>;
        }

        return <GifList gifList={gifList}/>;
    };

    return (
        <Container>
            <Row>
                <Col>
                    <SearchBar onClick={(text) => setSearchString(text)}/>
                </Col>
            </Row>
            <Row>
                <Col>
                   {content()}
                </Col>
            </Row>
        </Container>
    )
}

export default Home;
