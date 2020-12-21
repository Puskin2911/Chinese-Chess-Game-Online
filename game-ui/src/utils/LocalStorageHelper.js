const getCookie = (cname) => {
    const name = cname + "=";
    const decodedCookie = decodeURIComponent(document.cookie);
    const ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

const setCookie = (cname, cookieValue, expiredDays, isSecure) => {
    const d = new Date();
    d.setTime(d.getTime() + (expiredDays * 24 * 60 * 60 * 1000));
    const expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cookieValue + ";" + expires + ";path=/" + (isSecure ? ";secure" : "");
}

const deleteCookie = (name) => {
    document.cookie = name + '=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

const localStorageHelper = {
    setCookie,
    getCookie,
    deleteCookie
};

export default localStorageHelper;