import React, { useState } from "react";
import axios from "axios"
import { Button, Card } from "react-bootstrap";

const GifCard = ({ liked, gifId, imgSrcUrl }) => {
    const [isLiked, setLiked] = useState(liked);

    const onLike = () => {
        axios.post("http://localhost:8080/user/like",
            { giphyId: gifId },
            { withCredentials: true })
            .then(res => {
                setLiked(true);
            })
            .catch(error => {
                console.log(error);
            });
    };

    return (
        <Card style={{ width: '18rem' }}>
            <Card.Img variant="top" src={imgSrcUrl}/>
            <Card.Body>
                { isLiked ? ("Liked") : (<Button variant="primary"
                                          onClick={onLike}>Like</Button>)}

            </Card.Body>
        </Card>
    );
};

export default GifCard;