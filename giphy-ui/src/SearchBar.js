import React, { useState } from 'react';
import { Button, Form, FormControl } from "react-bootstrap";

const SearchBar = ({ onClick }) => {
    const [searchString, setSearchString] = useState('default');

    const onSearchChange = (event) => {
        setSearchString(event.target.value);
    };

    return (
        <Form inline>
            <FormControl type="text" placeholder="Search" className="mr-sm-2" value={searchString} onChange={onSearchChange}/>
            <Button variant="outline-success" onClick={() => onClick(searchString)}>Search</Button>
        </Form>
    );
};

export default SearchBar;