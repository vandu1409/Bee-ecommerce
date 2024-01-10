import axiosClient from "~/utils/axiosClient";

function checkoutApi() {
    return {
        checkout: (data) => {
            return axiosClient.post(`/checkout`, data);
        }
}
}

export default checkoutApi;