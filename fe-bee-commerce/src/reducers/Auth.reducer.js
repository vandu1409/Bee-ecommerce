import {AUTHENTICATE, LOGIN, LOGOUT, REMOVE_REFRESH_TOKEN, SET_ACCESS_TOKEN} from "~/action/constant/Auth";

const initialState = {
    accessToken: null,
    refreshToken: null,
    userData: null,
    vendor: {},
};

const authReducer = (state = initialState, {payload, type}) => {
    const { accessToken, refreshToken, ...userData } = payload || {};
    switch (type) {
        case SET_ACCESS_TOKEN:
            payload && localStorage.setItem("accessToken", payload);
            return {
                ...state,
                accessToken: payload,
            };
        case REMOVE_REFRESH_TOKEN:
            localStorage.removeItem("refreshToken");
            return {
                ...state,
                refreshToken: null,
            };
        case LOGIN:
            refreshToken && localStorage.setItem("refreshToken", refreshToken);
            accessToken && localStorage.setItem("accessToken", accessToken);
            return {
                ...state,
                accessToken,
                refreshToken,
                userData,
            };
        case LOGOUT:
            localStorage.removeItem("refreshToken");
            localStorage.removeItem("accessToken");
            return initialState;
        case AUTHENTICATE:
            return {
                ...state,
                userData,
            };
        default:
            return state;
    }
};

export default authReducer;
