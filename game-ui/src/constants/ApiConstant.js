const API_BASE_URL = 'http://127.0.0.1:8080';
const ACCESS_TOKEN = 'accessToken';

const OAUTH2_REDIRECT_URI = 'http://localhost:3000/oauth2/redirect'

const GOOGLE_AUTH_URL = API_BASE_URL + '/oauth2/authorize/google?redirect_uri=' + OAUTH2_REDIRECT_URI;
const FACEBOOK_AUTH_URL = API_BASE_URL + '/oauth2/authorize/facebook?redirect_uri=' + OAUTH2_REDIRECT_URI;
const GITHUB_AUTH_URL = API_BASE_URL + '/oauth2/authorize/github?redirect_uri=' + OAUTH2_REDIRECT_URI;

const CHECK_AUTH_URL = API_BASE_URL + '/api/auth/validate';
const LOGIN_URL = API_BASE_URL + '/api/auth/login';

const ApiConstants = {
    ACCESS_TOKEN,
    OAUTH2_REDIRECT_URI,
    GOOGLE_AUTH_URL,
    FACEBOOK_AUTH_URL,
    GITHUB_AUTH_URL,
    CHECK_AUTH_URL,
    LOGIN_URL
};

export default ApiConstants;