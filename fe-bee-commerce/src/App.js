import React, {useEffect} from "react";
import {BrowserRouter} from "react-router-dom";
import {useDispatch} from "react-redux";
import PropagateLoader from "./loading/PropagateLoader";
import authenticateApi from "~/api/authenticateApi";
import {authenticate} from "~/action/Auth.action";
import CustomRouter from "~/router/CustomRouter";
import {NotificationContainer, NotificationManager} from "react-notifications";
import {hideLoader, showLoader} from "~/action/Loader.action";


function App() {
    const dispatch = useDispatch();


    const { authenticateToken } = authenticateApi();

    // Revalidate token when refresh page
    useEffect(() => {
        dispatch(showLoader())
        authenticateToken()
            .then(({ data }) => {
                dispatch(authenticate(data));
            })
            .catch(() => NotificationManager.error("Phiên đăng nhập đã hết hạn"))
            .finally(() => {
                dispatch(hideLoader());
            });
    }, []);

    // noinspection JSValidateTypes
    return (
        <BrowserRouter>
            <div className="app">
                <CustomRouter/>
                <PropagateLoader />
                <NotificationContainer />
            </div>
        </BrowserRouter>
    );
}

export default App;
