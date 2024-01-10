export const  loaderSelector = (state) => state.loader;

export const loaderVisibleSelector = (state) => loaderSelector(state).loaderVisible;