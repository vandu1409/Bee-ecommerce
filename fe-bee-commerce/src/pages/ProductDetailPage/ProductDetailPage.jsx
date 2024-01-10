import React, { useEffect, useState } from "react";
import ProductDetail from "../../components/User/Info_Product/InfoProduct";
import InfoProductShop from "../../components/User/Info_Product_Shop/InfoProductShop";
import InfoProductDetail from "../../components/User/Info_Product/InfoProductDetail";
import ProductReview from "../../components/User/Info_Product/ProductReview";
import Wrapper from "../../components/common/Wrapper";
import { useParams } from "react-router-dom";
import productsApi from "~/api/productsApi";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import WishlistApi from "~/api/wishlistApi";
import feelbackApi from "~/api/feelbackApi";
import ProductTray from "~/components/User/Flash_Sale/ProductTray";

const ProductDetailPage = () => {
    const { selectedId } = useParams();

    console.log(selectedId);

    const { getProductById, getSuggestProduct } = productsApi();

    const dispatch = useDispatch();

    const [product, setProduct] = useState({});
    const [suggestProduct, setSuggestProduct] = useState([]);

    useEffect(() => {
        dispatch(showLoader());
        getProductById(selectedId)
            .then((response) => {
                setProduct(response.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    }, [selectedId]);

    const [feedback, setFeedback] = useState([]);
    const { getFeedback } = feelbackApi();

    useEffect(() => {
        dispatch(showLoader());
        getFeedback(selectedId)
            .then((response) => {
                setFeedback(response?.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
        getSuggestProduct(selectedId)
            .then((response) => {
                setSuggestProduct(response?.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    }, [selectedId]);

    console.log("feedbackaaaaaa", feedback);

    return (
        <>
            <div className="w-100 max-w-[1300px] mx-auto">
                <ProductDetail selectedId={selectedId} product={product} />

                <div className="grid grid-cols-2 gap-3">
                    <div>
                        <Wrapper className="mb-3 flex">
                            <InfoProductShop />
                        </Wrapper>
                    </div>
                    <Wrapper className="mb-3 flex">
                        <InfoProductDetail product={product} />
                    </Wrapper>
                </div>
                <ProductTray title="Sản phẩm tương tự" products={suggestProduct} />
                <Wrapper className="mb-3 flex">
                    {/*<ProductReview product={product} />*/}
                    <ProductReview feedback={feedback} />
                </Wrapper>
            </div>
        </>
    );
};

export default ProductDetailPage;
