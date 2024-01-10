import { combineReducers } from "redux";

import authReducer from "./Auth.reducer";
import loaderReducer from "./Loader.reducer";
import keySearchReducer from "./Search.reducer";



const rootReducer = combineReducers({
    auth: authReducer,
    loader: loaderReducer,
    keySearch: keySearchReducer,

});


export default rootReducer;
