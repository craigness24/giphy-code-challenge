import axios from "axios";


const HTTP = axios.create({
    withCredentials: true,
    baseURL: 'http://localhost:8080'
});

export default HTTP
