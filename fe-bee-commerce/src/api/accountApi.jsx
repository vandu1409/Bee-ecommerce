import axiosClient from "~/utils/axiosClient";
import {reverseCurrency} from "~/utils/CurrencyFormatter";

function accountApi() {
    return {
        updateInfo: (data) => {
            return axiosClient.put("/auth/update", data, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
        },
        deposit: (amount) => {
            return axiosClient.post("/payment/request-deposit?amount=" + reverseCurrency(amount));
        },
    };
}

export default accountApi;
