import React from 'react';
import './App.css';
import axios from 'axios'
import SearchBar from "./SearchBar";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoaded: false,
            data: [],
            error: undefined
        }
    }

    componentDidMount() {
        axios.get("http://localhost:8080/api/search?q=test")
            .then((res) => {
                this.setState({
                    data: res.data.data,
                    isLoaded: true
                });
            }, (rejected) => {
                this.setState({
                    data: [],
                    isLoaded: true,
                    error: rejected.message
                });
            })
            .catch((error) => {
                this.setState({
                    data: [],
                    isLoaded: true,
                    error: error.message
                });
            });
    }

    renderSquare(key, url) {
        return (
            <img key={key} src={url} alt="none"/>
        );
    }

    render() {
        const { error, isLoaded, data } = this.state;
        if (error) {
            return <div>Error: {error}</div>;
        }

        if (!isLoaded) {
            return <div>Loading...</div>;
        }

        const mp4s = data
            .map(d => this.renderSquare(d.id, d.images.fixed_height.url));

        return (
            <div className="App">
                <SearchBar onClick={(text) => this.onSearchSubmit(text)}/>
                {mp4s}
            </div>
        );
    }

    onSearchSubmit(text) {
        axios.get(`http://localhost:8080/api/search?q=${text}`)
            .then((res) => {
                this.setState({
                    data: res.data.data,
                    isLoaded: true
                });
            }, (rejected) => {
                this.setState({
                    data: [],
                    isLoaded: true,
                    error: rejected.message
                });
            })
            .catch((error) => {
                this.setState({
                    data: [],
                    isLoaded: true,
                    error: error.message
                });
            });
    }
}

export default App;
