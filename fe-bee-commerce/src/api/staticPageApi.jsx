import axiosClient from "~/utils/axiosClient";
import {checkProperties} from "~/utils/Helper";

function staticPageApi() {
    return {
        createPage: (data) => {
            checkProperties(data, ["title", "content","path"]);
            return axiosClient.post("/static-page",data)
        },
        getStaticPage: (id) => {
            return axiosClient.get(`/static-page/${id}`)
        },
        getStaticPages: (page = 0, size = 15) => {
            return axiosClient.get(`/static-page?page=${page}&size=${size}`)
        },
        deletePage: (id) => {
            return axiosClient.delete(`/static-page/${id}`)
        },
        updatePage: (id, data) => {
            checkProperties(data, ["title", "content","path"]);
            return axiosClient.put(`/static-page/${id}`, data)
        },
        getStaticPageByPath: (path) => {
            return axiosClient.get(`/static-page/path?p=${path}`)
        },
    };
}

export default staticPageApi;