import axiosClient from "~/utils/axiosClient";

export default function feelbackApi() {
    return {
        createFeedback: (formData) => {
            return axiosClient.post("/feedback", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
        },
        getFeedback: function (id) {
            return axiosClient.get(`/feedback?idProd=${id}`);
        },
    };
}
