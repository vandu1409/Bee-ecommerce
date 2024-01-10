import axiosClient from "~/utils/axiosClient";

export default function attributeApi() {
    return {
        getAttributesRequired: function (categoryId) {
            return axiosClient
            .get(`/attributes/require/${categoryId}`);
        }, 
        searchAttributes: function (keyword) {
            return axiosClient
            .get(`/attributes/search?key=${keyword}`);
        }
    }
}