import axios from "axios";
import ApiConstants from "../constants/ApiConstant";

const checkAuth = axios.get(
    ApiConstants.CHECK_AUTH_URL,
    {
        withCredentials: true
    }
);

const login = (loginInfo) => axios.post(
    ApiConstants.LOGIN_URL,
    loginInfo,
    {
        withCredentials: true
    }
);

const authService = {
    checkAuth,
    login
}

export default authService;
