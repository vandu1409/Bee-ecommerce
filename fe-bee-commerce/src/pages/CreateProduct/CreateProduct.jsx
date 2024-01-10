import React, { useEffect, useState } from "react";
import { Alert } from "react-bootstrap";
import "react-image-crop/src/ReactCrop.scss";
import Button from "~/components/common/Button";
import axiosClient from "~/utils/axiosClient";
import DetailInformation from "./DetailInformation";
import SalesInformation from "./SalesInformation";
import SimpleInformation from "./SimpleInformation";
import TransportInformation from "./TransportInformation";
import { useForm } from "react-hook-form";
import { dataURLtoFile } from "~/utils/Helper";
import productsApi from "~/api/productsApi";
import { NotificationManager } from "react-notifications";
import {useParams} from "react-router-dom";

export const CreateProductContext = React.createContext({});

function CreateProduct({}) {
    const { handleSubmit } = useForm();
    const { createProduct } = productsApi();
    const {productId} = useParams();

    console.log("Match", productId);

    // State này chứa thông tin giá và số lượng của từng phân loại dùng trong SalesInformation
    const [saleInfo, setSaleInfo] = useState([]);

    // State này chứa thông tin cơ bản của một sản phẩm
    const [simpleInfo, setSimpleInfo] = useState({
        name: "",
        categories: "",
        description: "",
        images: [],
    });

    // List các thuộc tính của sản phẩm đưa vào trong DetailInformation
    const [attributes, setAttributes] = useState([]);

    // State chứ tổng hợp các thông tin để tạo một  sản phẩm
    const [productData, setProductData] = useState({
        name: "",
        categories: "",
        categoryId: 0,
        description: "",
        images: [],
    });

    const [validator, setValidator] = useState([() => []]);

    const handleCreateProduct = () => {
        // Validate
        const errorMessage = [];
        validator.forEach((validate) => {
            const result = validate();
            if (!result) return;
            errorMessage.push(...(Array.isArray(result) ? result : [result]));
        });

        if (errorMessage.length !== 0) {
            alert(JSON.stringify(errorMessage));
        }
        const formData = new FormData();

        // Handle get Image form base64
        productData.images
            ?.map(({ data, fileName }) => dataURLtoFile(data, fileName))
            .forEach((img) => formData.append("images", img));

        const { images, ...productDataWithoutImages } = productData;

        formData.append("data", JSON.stringify(productDataWithoutImages));

        createProduct(formData)
            .then(() => {
                NotificationManager.success("Tạo sản phẩm thành công");
            })
            .catch((e) => {
                NotificationManager.error(e.message, "Tạo sản phẩm thất bại");
            });
    };

    useEffect(() => {
        document.title = "Tạo sản phẩm";
    }, []);

    useEffect(() => {
        setProductData({
            ...productData,
            classifies: saleInfo,
            attributes,
        });
    }, [saleInfo, simpleInfo, attributes]);

    return (
        <CreateProductContext.Provider
            value={{
                setValidator,
                productData,
                setProductData,
                simpleInfo,
                setSimpleInfo,
                saleInfo,
                setSaleInfo,
                attributes,
                setAttributes,
            }}
        >
            <div className="container w-100 max-w-5xl py-5">
                {/* From Status */}
                <div className="flex-1">
                    <SimpleInformation />
                    {productData.categoryId > 0 ? (
                        <>
                            <DetailInformation />
                            <SalesInformation />
                            <TransportInformation />
                            <Button onClick={handleCreateProduct} className="w-1/2 mx-auto mt-5">
                                Lưu
                            </Button>
                        </>
                    ) : (
                        <Alert className="text-center">Vui lòng chọn ngành hàng để tiếp tục</Alert>
                    )}
                </div>
            </div>
        </CreateProductContext.Provider>
    );
}

export default CreateProduct;
