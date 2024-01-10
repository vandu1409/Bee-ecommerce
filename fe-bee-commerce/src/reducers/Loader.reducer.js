import {HIDE_LOADER, SHOW_LOADER, TOGGLE_LOADER} from "~/action/constant/Loader";

const initialState = []

const loaderReducer = (state = initialState, action) => {
    switch (action.type) {
        case SHOW_LOADER:
            return [...state, 1]
        case HIDE_LOADER:
            return state.slice(1)
        // case TOGGLE_LOADER:
        //     return {
        //         loaderVisible: !state.loaderVisible,
        //     };
        default:
            return state;
    }
};

export default loaderReducer;
