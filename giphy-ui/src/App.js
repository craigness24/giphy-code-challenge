import React from 'react';
import './App.css';
import axios from 'axios'

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoaded: false,
            data: []
        }
    }

    componentDidMount() {
        axios.get("http://localhost:8080")
            .then(res => {
                this.setState({
                    data: res.data.data,
                    isLoaded: true
                });
            })
            .catch(
                (error) => {
                    this.setState({
                        data: [],
                        isLoaded: true
                    });
                });

    }

    renderSquare(key, url) {
        return (
            <img key={key} src={url} alt="none"/>
        );
    }

    render() {
        const mp4s = this.state.data
            .map(d => this.renderSquare(d.id, d.images.fixed_height.url));

        return (
            <div className="App">
                {mp4s}
            </div>
        );
    }
}

export default App;
