// const API_BASE_URL = 'https://127.0.0.1:8080';
const API_BASE_URL = 'https://ec2-54-254-252-59.ap-southeast-1.compute.amazonaws.com:8080';
const ACCESS_TOKEN = 'accessToken';

const OAUTH2_REDIRECT_URI = 'https://localhost:3000/oauth2/redirect'

const GOOGLE_AUTH_URL = API_BASE_URL + '/oauth2/authorize/google?redirect_uri=' + OAUTH2_REDIRECT_URI;
const FACEBOOK_AUTH_URL = API_BASE_URL + '/oauth2/authorize/facebook?redirect_uri=' + OAUTH2_REDIRECT_URI;
const GITHUB_AUTH_URL = API_BASE_URL + '/oauth2/authorize/github?redirect_uri=' + OAUTH2_REDIRECT_URI;

const BASE_AUTH_API_URL = '/api/auth';
const CHECK_AUTH_URL = API_BASE_URL + BASE_AUTH_API_URL + '/validate';
const LOGIN_URL = API_BASE_URL + BASE_AUTH_API_URL + '/login';
const SIGNUP_URL = API_BASE_URL + BASE_AUTH_API_URL + '/signup';

const BASE_ROOM_URL = '/api/rooms';
const JOIN_ROOM_URL = API_BASE_URL + BASE_ROOM_URL + '/join';
const LEAVE_ROOM_URL = (roomId) => API_BASE_URL + BASE_ROOM_URL + '/' + roomId + '/leave';
const GET_AVAILABLE_URL = API_BASE_URL + BASE_ROOM_URL + "/available"

const SOCKET_CONNECT_URL = API_BASE_URL + '/stomp';

const CHAT_SOCKET_URL = (roomId) => "/room/" + roomId + "/chat";

const ApiConstants = {
    ACCESS_TOKEN,
    OAUTH2_REDIRECT_URI,
    GOOGLE_AUTH_URL,
    FACEBOOK_AUTH_URL,
    GITHUB_AUTH_URL,
    CHECK_AUTH_URL,
    LOGIN_URL,
    SIGNUP_URL,
    JOIN_ROOM_URL,
    LEAVE_ROOM_URL,
    CHAT_SOCKET_URL,
    GET_AVAILABLE_URL,
    SOCKET_CONNECT_URL
};

export default ApiConstants;