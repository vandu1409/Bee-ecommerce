import {HIDE_LOADER, SHOW_LOADER, TOGGLE_LOADER} from "./constant/Loader";

export const showLoader = () => {
      return {
            type: SHOW_LOADER,
      };
};

export const hideLoader = () => {
      return {
            type: HIDE_LOADER,
      };
};

export const toggleLoader = () => {
      return {
            type: TOGGLE_LOADER,
      };
}