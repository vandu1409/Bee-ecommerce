import { CLEAR_KEY_WORD, SET_KEY_WORD } from "./constant/Search";

export const setSearckKey = (payload = "") => {
      return {
            type: SET_KEY_WORD,
            payload,
      };
}


//CLEAR_KEY_WORD
export const clearSearchKey = (payload = "") => {
      return {
            type: CLEAR_KEY_WORD,
            payload,
      };
}