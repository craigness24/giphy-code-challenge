import React from 'react';

class SearchBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            searchString: 'default'
        };
    }

    onSearchChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    render() {
        return (
            <div>
                <input name="searchString" value={this.state.searchString} onChange={this.onSearchChange}/>
                <button type="submit" onClick={() => this.props.onClick(this.state.searchString)}>Search</button>
            </div>
        );
    }
}

export default SearchBar;