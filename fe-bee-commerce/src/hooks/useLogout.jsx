import {useDispatch} from "react-redux";
import authenticateApi from "~/api/authenticateApi";
import {logout} from "~/action/Auth.action";

function useLogout() {

    const dispatch = useDispatch();
    const {logout: logoutApi} = authenticateApi();

    return () => {
        return logoutApi().then(() => {
            dispatch(logout());
        });
    }

}

export default useLogout;