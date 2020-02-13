import React, { useState } from "react";
import { Button, Card, Form, FormControl } from "react-bootstrap";
import HTTP from "../config/app-config";
import Badge from "react-bootstrap/Badge";

const GifCard = ({ categories, liked, gifId, imgSrcUrl }) => {
    const [isLiked, setLiked] = useState(liked);
    const [categoryString, setCategoryString] = useState('');

    function save(likeInput) {
        HTTP.post("/user/giphy",
            {
                giphyId: gifId,
                categories: categories,
                liked: likeInput
            })
            .then(res => {
                setLiked(likeInput);
                setCategoryString('');
            })
            .catch(error => {
                console.log(error);
            });
    }

    const onLike = () => {
        save(!liked);
    };

    const onClick = () => {
        let find = categories.find(c => c === categoryString);
        if (!find) {
            categories.push(categoryString);
            save(liked);
        }
    };

    return (
        <Card style={{ width: '18rem', display: 'inline-block' }}>
            <Card.Img variant="top" src={imgSrcUrl}/>
            <Card.Body>
                <Card.Subtitle className="mb-2 text-muted">
                    {categories.map(category => <Badge key={category} variant="secondary">{category}</Badge>)}
                </Card.Subtitle>
                <Form inline>
                    <Form.Check
                        type={"checkbox"}
                        label={"Liked"}
                        checked={liked}
                        onChange={onLike}
                    />
                    <FormControl type="text" placeholder="Category" className="mr-sm-2" value={categoryString}
                                 onChange={(event) => setCategoryString(event.target.value)}/>
                    <Button variant="outline-success" onClick={onClick}>Add</Button>
                </Form>
            </Card.Body>
        </Card>
    );
};

export default GifCard;
