import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { AiOutlineUnorderedList, AiFillCaretRight } from "react-icons/ai";
import Wrapper from "~/components/common/Wrapper";
import categoriesApi from "~/api/categoriesApi";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import productsApi from "~/api/productsApi";
import ProductTray from "~/components/User/Flash_Sale/ProductTray";
import CategoriesBar from "~/components/User/CategoriesBar/CategoriesBar";
const CategoryDetailPage = () => {
    const { id, name } = useParams();
    const { getCategoryByparentid } = categoriesApi();
    const { getProductByCategoryId, getAllProductParentId } = productsApi();
    const dispatch = useDispatch();
    const [listCategory, setListCategory] = useState([]);
    const [listProduct, setListProduct] = useState([]);
    const [activeIndex, setActiveIndex] = useState(null);

    const [selectedCategoryId, setSelectedCategoryId] = useState(null);
    const handleCategoryClick = (index) => {
        setActiveIndex(index);
    };

    const handleClick = (categoryId) => {
        setSelectedCategoryId(categoryId);
    };

    const fetchCategoryData = () => {
        dispatch(showLoader());

        getCategoryByparentid(id)
            .then((response) => {
                setListCategory(response.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    };

    const fetchProductData = () => {
        dispatch(showLoader());
        getAllProductParentId(id)
            .then((response) => {
                setListProduct(response.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    };

    const fetchProductByCategoryId = () => {
        dispatch(showLoader());

        getProductByCategoryId(selectedCategoryId)
            .then((response) => {
                setListProduct(response.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    };

    useEffect(() => {
        fetchCategoryData();
        fetchProductData();
    }, [id]);

    useEffect(() => {
        if (selectedCategoryId !== null) {
            fetchProductByCategoryId();
        }
    }, [selectedCategoryId]);

    return (
        <>
            <div className="w-100 max-w-[1200px] mx-auto">
                <div className="flex">
                    <div className="w-[260px] pr-2">
                        {/*<Wrapper className=" p-4">*/}
                        {/*    <div className="mb-3">*/}
                        {/*        <div className="flex items-center py-2">*/}
                        {/*            <AiOutlineUnorderedList className="mr-[10px] " />*/}
                        {/*            <h4 className="  text-[rgb(56,56,61)] text-[17px] font-bold m-0 px-0 ">*/}
                        {/*                Tất cả danh mục*/}
                        {/*            </h4>*/}
                        {/*        </div>*/}

                        {/*        <div className="pt-1">*/}
                        {/*            <div*/}
                        {/*                className={`row flex items-center cursor-pointer mb-2 ${*/}
                        {/*                    activeIndex === null ? "active-category" : "category-item-title"*/}
                        {/*                }`}*/}
                        {/*            >*/}
                        {/*                <div className="col-1">*/}
                        {/*                    {activeIndex === null ? (*/}
                        {/*                        <AiFillCaretRight className={`text-[#ee4d2d] `} />*/}
                        {/*                    ) : (*/}
                        {/*                        ""*/}
                        {/*                    )}*/}
                        {/*                </div>*/}
                        {/*                <div className="col-9">*/}
                        {/*                    <span onClick={() => handleCategoryClick(null)}>{name}</span>*/}
                        {/*                </div>*/}
                        {/*            </div>*/}
                        {/*        </div>*/}
                        {/*    </div>*/}
                        {/*</Wrapper>*/}
                        <CategoriesBar/>
                    </div>
                    <div className="w-[1100px] ml-3 ">
                        {/*<Wrapper className="bg-white  flex p-3 ">*/}
                        {/*    <div className="search-query-active">*/}
                        {/*        <span>Phổ biến</span>*/}
                        {/*    </div>*/}
                        {/*    <div className="search-query">*/}
                        {/*        <span>Bán chạy</span>*/}
                        {/*    </div>*/}
                        {/*    <div className="search-query">*/}
                        {/*        <span>Giá cao đến thấp</span>*/}
                        {/*    </div>*/}
                        {/*    <div className="search-query">*/}
                        {/*        <span>Giá thấp đến cao</span>*/}
                        {/*    </div>*/}
                        {/*</Wrapper>*/}

                        <div className="row m-0">
                            <ProductTray key={1} title="" products={listProduct} />
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
};

export default CategoryDetailPage;
