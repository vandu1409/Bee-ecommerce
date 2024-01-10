export const authSelector = (state) => state.auth;

export const accessTokenSelector = (state) => authSelector(state).accessToken;

export const refreshTokenSelector = (state) => authSelector(state).refreshToken;

export const userDataSelector = (state) => authSelector(state).userData;

