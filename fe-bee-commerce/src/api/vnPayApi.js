import axiosClient from "~/utils/axiosClient";

function vnPayApi() {
    return {
        checksum: (data) => {
            return axiosClient.post("/payment/checksum", data);
        },
    };
}

export default vnPayApi;
