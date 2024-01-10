import {useNavigate, useSearchParams} from "react-router-dom";
import authenticateApi from "~/api/authenticateApi";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";

function ConformAccount({ children }) {
    const [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate();
    const { confirm_accountApi } = authenticateApi();

    const token = searchParams.get("token");

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(showLoader());
        confirm_accountApi({token})
            .then((response) => {
                NotificationManager.success("Xác nhận tài khoảng thành công", "Thành công");
            })
            .catch(({ data }) => {
                NotificationManager.error("Xát nhận tài khoảng thất bại", "Lỗi");
            })
            .finally(() => {
                dispatch(hideLoader());
                navigate("/login");
            });
    });

    return <h1>Trang xát nhận tài khoảng</h1>;
}

export default ConformAccount;
