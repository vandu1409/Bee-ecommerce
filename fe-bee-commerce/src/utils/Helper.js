import { parse, format } from "date-fns";
import errImg from "~/Images/not-found.png";
export function checkProperties(data, properties = []) {
    properties.forEach((item) => {
        if (data[item] === undefined || data[item] === null) {
            throw new Error(`${item} is required`);
        }
    });
}

export function uniqueArray(arr, comparator) {
    if (!arr || !Array.isArray(arr)) {
        throw new Error("Input must be a valid array");
    }

    if (comparator && typeof comparator !== "function") {
        throw new Error("Comparator must be a function");
    }

    if (!comparator) {
        comparator = (a, b) => a === b;
    }

    let isUnique = true;

    const uniqueArr = arr.reduce((acc, item) => {
        if (!acc.some((x) => comparator(x, item))) {
            acc.push(item);
        } else {
            isUnique = false;
        }

        return acc;
    }, []);

    return isUnique ? uniqueArr : { array: uniqueArr, isUnique };
}
// Replace the Blob URL with your actual Blob URL
export const blobURLtoBase64 = async (blobUrl) => {
    try {
        // Fetch the Blob data
        const response = await fetch(blobUrl);
        const blob = await response.blob();

        // Convert Blob to Data URL
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onloadend = function () {
                const dataUrl = reader.result;
                resolve(dataUrl);
            };
            reader.onerror = function (error) {
                reject(error);
            };
            reader.readAsDataURL(blob);
        });
    } catch (error) {
        console.error("Error fetching Blob:", error);
        throw error;
    }
};

export const getTypeFromBase64 = (base64) => {
    return base64.split(";")[0]?.split(":")[1];
};

export function dataURLtoFile(dataUrl, filename) {
    // Split the data URL to get the MIME type and the base64-encoded data
    const dataUrlParts = dataUrl.split(",");
    const mimeType = dataUrlParts[0].match(/:(.*?);/)[1];
    const base64Data = atob(dataUrlParts[dataUrlParts.length - 1]);
    let dataLength = base64Data.length;
    const byteCharacters = new Uint8Array(dataLength);

    // Decode the base64 data and store it in a Uint8Array
    while (dataLength--) {
        byteCharacters[dataLength] = base64Data.charCodeAt(dataLength);
    }

    // Create a File object from the Uint8Array
    return new File([byteCharacters], filename, { type: mimeType });
}

export function convertDate(dateString) {
    if (!dateString) return "";
    const date = parse(dateString, "yyyyMMddHHmmss", new Date());
    return format(date, "HH:mm:ss dd-MM-yyyy");
}

export function handleImgError(e) {
    e.target.src = errImg;
}

export function getOrderStatus({status, paymentMethod}) {

    if (status === "PENDING") {
        if (paymentMethod === "VNPAY") {
            return "Đang chờ thanh toán";
        }
        return "Đang xử lý";
    } else if (status === "PAID") {
        return "Đã thanh toán";

    } else if (status === "CONFIRMED") {
        return "Đã xác nhận";
    } else if (status === "DELIVERING") {
        return "Đang giao hàng";
    } else if (status === "COMPLETED") {
        return "Đã giao hàng";
    } else if (status === "CANCELLED") {
        return "Đã hủy";
    }
    return "Trạng thái không xác định";
}

export function getButtonText({status, paymentMethod}) {
    let message = "";
    let isButton = true;
    if (status === "PENDING") {
        if (paymentMethod === "VNPAY") {
            message = "Đợi người mua thanh toán";
            isButton = false;
        } else {
            message = "Xác nhận đơn hàng";
            isButton = true;
        }
    } else if (status === "PAID") {
        message = "Xác nhận đơn hàng";
        isButton = true;
    } else if (status === "CONFIRMED") {
        message = "Giao hàng";
        isButton = true;
    } else if (status === "DELIVERING") {
        message = "Đơn hàng đang giao";
        isButton = false;
    } else if (status === "COMPLETED") {
        message = "Đơn hàng đã giao thành công" ;
        isButton = false;
    } else if (status === "CANCELLED") {
        message = "Đơn hàng đã hủy";
        isButton = false;
    }
    return {message, isButton};
}
