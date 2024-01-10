import { CLEAR_KEY_WORD, SET_KEY_WORD } from "~/action/constant/Search";

const initialState = ""

const keySearchReducer = (state = initialState, { payload, type }) => {
      switch (type) {
            case SET_KEY_WORD:
                  console.log("payloadKey", payload)
                  return payload;
            case CLEAR_KEY_WORD:
                  return "";
            default:
                  return state;
      }
};

export default keySearchReducer;

