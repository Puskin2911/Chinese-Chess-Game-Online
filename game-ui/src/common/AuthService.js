import axios from "axios";
import {CHECK_AUTH_URL, LOGIN_URL} from "./constants";

export const checkAuth = axios.get(
    CHECK_AUTH_URL,
    {
        withCredentials: true
    }
);

export const login = (userInfo) => axios.post(
    LOGIN_URL,
    userInfo,
    {
        withCredentials: true
    }
);