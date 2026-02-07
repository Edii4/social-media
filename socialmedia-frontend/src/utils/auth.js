const TOKEN_KEY = "token";
const USER_ID_KEY = "userId";
const USERNAME_KEY = "username";

// ----- TOKEN -----
export const setToken = (token) => {
    localStorage.setItem(TOKEN_KEY, token);
};

export const getToken = () => {
    return localStorage.getItem(TOKEN_KEY);
};

export const removeToken = () => {
    localStorage.removeItem(TOKEN_KEY);
};

// ----- USER -----
export const setUser = ({ userId, username }) => {
    if (userId) localStorage.setItem(USER_ID_KEY, userId);
    if (username) localStorage.setItem(USERNAME_KEY, username);
};

export const getUserId = () => {
    return localStorage.getItem(USER_ID_KEY);
};

export const getUsername = () => {
    return localStorage.getItem(USERNAME_KEY);
};

export const removeUser = () => {
    localStorage.removeItem(USER_ID_KEY);
    localStorage.removeItem(USERNAME_KEY);
};

// ----- AUTH STATE -----
export const isLoggedIn = () => {
    return !!getToken();
};

export const logout = () => {
    removeToken();
    removeUser();
};
