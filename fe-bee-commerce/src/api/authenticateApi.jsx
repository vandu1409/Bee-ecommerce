import axiosClient from "~/utils/axiosClient";
import {checkProperties} from "~/utils/Helper";

export default function authenticateApi() {
    return {
        register: function (data) {
            checkProperties(data, ["email", "username", "fullname", "phoneNumber", "password", "confirmPassword"]);
            return axiosClient.post("/auth/register", data);
        },
        loginApi: function (data) {
            checkProperties(data, ["username", "password"]);
            return axiosClient.post("/auth/login", data);
        },
        logout: function () {
            return axiosClient.post("/auth/logout");
        },
        forgot_passwordApi: function (data) {
            checkProperties(data, ["username"]);
            return axiosClient.post("/auth/forgot-password", data);
        },
        confirm_accountApi: function (data) {
            checkProperties(data, ["token"]);
            return axiosClient.put("/auth/confirm-account", data);
        },
        resetPasswordApi: function (data) {
            checkProperties(data, ["token", "password", "confirmPassword"]);
            return axiosClient.post("/auth/reset-password", data);
        },
        authenticateToken: function () {
            return axiosClient.post("/auth/validate", null, {
                headers: {
                    Authorization: undefined,
                },
            });
        },
    };
}
