import {AUTHENTICATE, LOGIN, LOGOUT, REMOVE_REFRESH_TOKEN, SET_ACCESS_TOKEN} from "~/action/constant/Auth";

const template = {
    accessToken: null,
    refreshToken: null,
    userData: null,
};

export const login = (payload = template) => {
    return {
        type: LOGIN,
        payload,
    };
};

export const logout = () => {
    return {
        type: LOGOUT,
    };
};

export const setAccessToken = (payload = "") => {
    return {
        type: SET_ACCESS_TOKEN,
        payload,
    };
};

export const removeRefreshToken = () => {
    return {
        type: REMOVE_REFRESH_TOKEN,
    };
};

export const authenticate = (payload) => {
    return {
        type: AUTHENTICATE,
        payload,
    }
}