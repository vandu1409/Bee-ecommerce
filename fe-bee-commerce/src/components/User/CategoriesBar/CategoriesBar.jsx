import React, {useEffect, useState} from "react";
import "./CategoriesBar.scss";
import { Link } from "react-router-dom";
import {handleImgError} from "~/utils/Helper";
import defaultImg from "~/Images/not-found.png";
import {hideLoader, showLoader} from "~/action/Loader.action";
import {NotificationManager} from "react-notifications";
import {useDispatch} from "react-redux";
import categoriesApi from "~/api/categoriesApi";
const CategoriesBar = ({ listCategoryz }) => {
    const { getAllCategoryInproduct } = categoriesApi();
    const dispatch = useDispatch();
    const [listCategory, setListCategory] = useState([]);
    useEffect(() => {
        dispatch(showLoader());
        getAllCategoryInproduct()
            .then((response) => {
                setListCategory(response.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    }, []);
    return (
        <div className="categories-bar bg-tw-white rounded-xl shadow-md">
            <div className="tieuDe">
                <p>Ngành hàng</p>
            </div>
            {listCategory.map((category, index) => (
                <Link
                    to={`/categories/${category.id}/${category.name}`}
                    key={index}
                    className="cursor-pointer text-mn flex items-center px-2 rounded-md space-x-2 hover:bg-[#e7e7e7]"
                >
                    <img
                        src={category.posterUrl || defaultImg}
                        onError={handleImgError}
                        alt="Cate"
                        className="img-fluid flex-shrink w-[50px] h-[50px]"
                    />

                    <div>
                        <p className="leading-2 max-w-[calc(100% - 50px)] text-[14px]">{category.name}</p>
                    </div>
                </Link>
            ))}
        </div>
    );
};

export default CategoriesBar;
