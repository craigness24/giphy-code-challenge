import React, { useState } from "react";
import { Button, Card } from "react-bootstrap";
import HTTP from "../config/app-config";

const GifCard = ({ liked, gifId, imgSrcUrl }) => {
    const [isLiked, setLiked] = useState(liked);

    const onLike = () => {
        HTTP.post("/user/like",
            { giphyId: gifId })
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
