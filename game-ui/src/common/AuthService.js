import axios from "axios";
import {CHECK_AUTH_URL, LOGIN_URL} from "./constants";

export default class AuthService {
    checkAuth = axios.get(
        CHECK_AUTH_URL,
        {
            withCredentials: true
        }
    );

    login = (loginInfo) => axios.post(
        LOGIN_URL,
        loginInfo,
        {
            withCredentials: true
        }
    );
}