import axiosClient from "~/utils/axiosClient";

export default function searchapi() {
    return {
        search: (keyword) => {
            return axiosClient.get(`/search?keyword=${keyword}`);
        },
    };
}
