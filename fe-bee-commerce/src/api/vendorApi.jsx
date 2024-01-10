import axiosClient from "~/utils/axiosClient";
import {checkProperties} from "~/utils/Helper";


export default function vendorApi() {
    return {
        registerVendor: (data) => {
            checkProperties(data, ["name", "wardId"])
            return axiosClient.post("/vendors", data)
        },
        getVendorById: (id) => {
            return axiosClient.get(`/vendors/${id}`)
        },
        getProductByVendorId: (id) => {
            return axiosClient.get(`/products/vendor/${id}`)
        }
    }
}