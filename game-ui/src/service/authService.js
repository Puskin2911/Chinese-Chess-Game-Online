import axios from "axios";
import {CHECK_AUTH_URL, LOGIN_URL} from "./constants";


const checkAuth = axios.get(
    CHECK_AUTH_URL,
    {
        withCredentials: true
    }
);

const login = (loginInfo) => axios.post(
    LOGIN_URL,
    loginInfo,
    {
        withCredentials: true
    }
);

export default {
    checkAuth,
    login
}
