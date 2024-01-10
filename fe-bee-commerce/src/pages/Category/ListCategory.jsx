import React, { useEffect, useState } from "react";
import categoriesApi from "~/api/categoriesApi";
import Wrapper from "~/components/common/Wrapper";
import CustomPagination from "~/components/common/CustomPagination";
import { Link, useSearchParams } from "react-router-dom";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import { Button } from "react-bootstrap";

const ListCategory = () => {
    const [searchParam, setSearchParam] = useSearchParams();
    const dispatch = useDispatch();

    const [categories, setCategories] = useState([]);
    const [currentPage, setCurrentPage] = useState(searchParam.get("page") || 0);
    const [pageCount, setPageCount] = useState(0);
    const [maxLevel, setMaxLevel] = useState(1);

    const handlePageClick = (selectedPage) => {
        setCurrentPage(selectedPage);
    };

    const { getAllCategory, deleteCategory } = categoriesApi();
    useEffect(() => {
        dispatch(showLoader());
        getAllCategory(currentPage)
            .then(({ pagination, data }) => {
                let max = 0;
                const clearData = data.map(({ parentCategories, ...item }) => {
                    const categories = [];
                    categories[item.level - 1] = item;
                    max = Math.max(max, item.level);
                    parentCategories.reduce((acc, category) => {
                        acc[category.level - 1] = category;
                        return acc;
                    }, categories);
                    return {
                        ...item,
                        categories: categories,
                    };
                });

                console.log("Data", clearData);

                setPageCount(pagination?.totalPages);
                console.log(pagination?.totalPages);

                searchParam.set("page", currentPage);
                setSearchParam(searchParam);
                setCategories(clearData);
                setMaxLevel(max);
            })
            .catch((error) => {
                console.error(error);
            })
            .finally(() => dispatch(hideLoader()));
    }, [currentPage]);

    useEffect(() => {
        document.title = "Danh sách ngành hàng";
    }, []);

    const handleDeteleCategory = (id) => {
        dispatch(showLoader());
        deleteCategory(id)
            .then(({ response }) => {
                NotificationManager.success(response, "Xóa thành công");
                setCategories((prevCategories) => prevCategories.filter((category) => category.id !== id));
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => {
                dispatch(hideLoader());
            });
    };

    return (
        <div className="w-100 max-w-[1300px] mx-auto bg-white ">
            <Wrapper className="p-3 min-h-[500px]">
                <div className="flex items-center justify-between">
                    <div>
                        <h2 className="text-2xl font-bold mb-4">Danh sách ngành hàng</h2>
                    </div>
                    <div>
                        <Button variant="outline-primary" className=" py-2 px-3">
                            <Link to={"/categories/create"} className="font-bold">
                                Tạo mới ngành hàng
                            </Link>
                        </Button>
                    </div>
                </div>
                <table className="table table-hover table-bordered table-striped">
                    <thead className="table-dark">
                        <tr>
                            {/*th{Ngành hàng cấp $}*5*/}
                            {Array.from({ length: maxLevel }, (_, index) => (
                                <th>Ngành hàng cấp {index + 1}</th>
                            ))}
                            <th>Ảnh </th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        {categories.map((category) => {
                            return (
                                <tr>
                                    {Array.from({ length: maxLevel }, (_, index) => {
                                        const categoryItem = category.categories[index];
                                        if (categoryItem) {
                                            return (
                                                <td>
                                                    <Link to="" className="flex align-items-center h-[50px]">
                                                        {categoryItem.name}
                                                    </Link>
                                                </td>
                                            );
                                        }
                                        return <td>-</td>;
                                    })}
                                    <td>
                                        <img
                                            className="w-[50px] h-[50px] border-[0.5px] border-dark-400"
                                            src={category?.posterUrl}
                                            alt="Poster"
                                        />
                                    </td>
                                    <td>
                                        <Link to={`/admin/categories/edit/${category.id}`} className="btn btn-success mr-2 ">
                                            Chi tiết
                                        </Link>

                                        <button
                                            className="btn btn-danger"
                                            onClick={() => handleDeteleCategory(category.id)}
                                        >
                                            Xóa
                                        </button>
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
                <CustomPagination onChange={handlePageClick} currentPage={currentPage} totalPages={pageCount} />
            </Wrapper>
        </div>
    );
};

export default ListCategory;
