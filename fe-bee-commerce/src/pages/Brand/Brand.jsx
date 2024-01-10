import React, { useEffect, useState } from "react";
import Wrapper from "~/components/common/Wrapper";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import { FloatingLabel, Image, Modal } from "react-bootstrap";
import brandsApi from "~/api/brandsApi";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader, toggleLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import { useSearchParams } from "react-router-dom";
import CustomPagination from "~/components/common/CustomPagination";
import Badge from "~/components/common/Badge";
import { IoImageOutline } from "react-icons/io5";

const Brand = () => {
    const dispatch = useDispatch();
    const [selectedImage, setSelectedImage] = useState(null);
    const [image, setImage] = useState(null);
    const [id, setId] = useState(null);
    const [name, setName] = useState("");
    const [searchParam, setSearchParam] = useSearchParams();
    const [currentPage, setCurrentPage] = useState(searchParam.get("page") || 0);
    const [pageCount, setPageCount] = useState(0);
    const { createBrand, UpdateCategory, deleteCategory, getAllBrand, getBrandById } = brandsApi();
    const [brands, setBrand] = useState([]);
    const handleImageChange = (event) => {
        const file = event.target.files[0];
        const imageUrl = file ? URL.createObjectURL(file) : null;
        setSelectedImage(imageUrl);
        setImage(file);
    };

    const handlePageClick = (selectedPage) => {
        setCurrentPage(selectedPage);
    };

    const handleSelectBrand = (id) => {
        setId(id);
    };

    const resetData = () => {
        setId("");
        setName("");
        setSelectedImage(null);
        setImage(null);
    };

    const onSubmit = () => {
        if (!name.trim()) {
            NotificationManager.error("Tên thương hiệu không được trống", "Lỗi");
            return;
        }

        const formData = new FormData();
        formData.append("name", name);
        formData.append("image", image);

        if (id) {
            dispatch(showLoader());

            UpdateCategory(id, formData)
                .then((response) => {
                    dispatch(hideLoader());
                    NotificationManager.success(response.data, "Thành công");
                })
                .catch((error) => {
                    dispatch(hideLoader());
                    NotificationManager.error(error.message, "Lỗi");
                })
                .finally(() => {
                    dispatch(hideLoader());
                    getAllBrands(currentPage);
                });
        } else {
            dispatch(showLoader());

            createBrand(formData)
                .then((response) => {
                    dispatch(hideLoader());
                    NotificationManager.success(response.data, "Thành công");
                })
                .catch((error) => {
                    dispatch(hideLoader());
                    NotificationManager.error(error.message, "Lỗi");
                })
                .finally(() => {
                    dispatch(hideLoader());
                    getAllBrands(currentPage);
                    resetData();
                });
        }
    };

    const getAllBrands = (currentPage) => {
        dispatch(toggleLoader());
        getAllBrand(currentPage)
            .then(({ pagination, data }) => {
                setPageCount(pagination?.totalPages);

                searchParam.set("page", currentPage);
                setSearchParam(searchParam);
                setBrand(data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    };

    useEffect(() => {
        getAllBrands(currentPage);
    }, [currentPage]);

    useEffect(() => {
        if (id) {
            dispatch(showLoader());
            getBrandById(id)
                .then((response) => {
                    setName(response.data.name);
                    setSelectedImage(response.data.url);
                })
                .catch((error) => {
                    NotificationManager.error("Không lấy được data brand", "Lỗi");
                })
                .finally(() => {
                    dispatch(hideLoader());
                });
        }
    }, [id]);

    const handleDeteleBrand = (id) => {
        dispatch(showLoader());
        deleteCategory(id)
            .then(({ response }) => {
                NotificationManager.success(response, "Xóa thành công");
                setBrand((prevCategories) => prevCategories.filter((category) => category.id !== id));
            })
            .catch((error) => {
                NotificationManager.warning("Thương hiệu này đã có sản phẩm sử dụng, không thể xoá.", "Cảnh báo");
            })
            .finally(() => {
                dispatch(hideLoader());
                clearForm();
            });
    };

    function clearForm() {
        setId(null);
        setName("");
        setSelectedImage(null);
        setImage(null);
    }

    return (
        <>
            <Modal show={id !== null} onHide={() => setId(null)}>
                <div className="p-4 mb-3 flex flex-col gap-2 justify-center items-center">
                    <h1 className="text-2xl font-semibold">{id ? "Cập nhật" : "Tạo mới"} thương hiệu</h1>
                    <div className="group/brandImg relative border-2 border-dark-400 rounded-full object-fill overflow-hidden h-[112px] w-[112px] mb-3">
                        <img className="w-100 h-100 object-fit-contain" src={selectedImage} alt="" />
                        <label
                            htmlFor="brandImg"
                            className="absolute top-100 group-hover/brandImg:!top-[63%] ease-in-out duration-300 left-0 w-full h-full bg-dark-950 bg-opacity-40 cursor-pointer flex justify-center pt-3"
                        >
                            <IoImageOutline className="text-xl text-dark-50" />
                        </label>
                    </div>
                    <FloatingLabel controlId="floatingInput" label="Tên thương hiệu" className="mb-3 ">
                        <Form.Control
                            type="text"
                            value={name}
                            className="w-100 max-w-[250px]"
                            placeholder="Nhập tên thương hiệu"
                            onChange={(e) => setName(e.target.value)}
                        />
                    </FloatingLabel>
                    <input id="brandImg" type="file" className="hidden" onChange={(e) => handleImageChange(e)} />
                    <div className="flex gap-2">
                        <Button onClick={onSubmit} variant="primary" className="font-bold py-1 px-5">
                            {id ? "Cập nhật" : "Tạo mới"}
                        </Button>
                        <Button onClick={clearForm} variant="primary" className="font-bold py-1 px-5">
                            Thoát
                        </Button>
                    </div>
                </div>
            </Modal>
            <Wrapper className="p-5 ">
                <div className="flex justify-between mb-3">
                    <h1 className="font-bold text-[25px]">Danh sách thương hiệu</h1>
                    <Button onClick={() => setId("")} variant="primary" className="font-bold py-1 px-5">
                        Tạo mới
                    </Button>
                </div>
                <Row className="mb-2 p-0 m-0">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col" className="w-[100px]">
                                    Hỉnh ảnh
                                </th>
                                <th scope="col" className="w-[450px]">
                                    Tên
                                </th>
                                <th scope="col">Số lượng sản phẩm</th>
                                <th scope="col">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            {brands.map((brand, index) => (
                                <tr key={index}>
                                    <th>
                                        <Image
                                            src={brand.url}
                                            alt="poster"
                                            className="rounded-lg w-[40px] h-[40px] object-contain object-fill"
                                            rounded
                                        />
                                    </th>
                                    <td>{brand.name}</td>
                                    <td>{brand.productCount}</td>
                                    <td className="space-x-3">
                                        <Badge
                                            onClick={() => handleSelectBrand(brand.id)}
                                            className="btn btn-outline-info mr-2 cursor-pointer"
                                        >
                                            Chi tiết
                                        </Badge>
                                        <Badge
                                            type={"danger"}
                                            onClick={() => handleDeteleBrand(brand.id)}
                                            className="btn btn-outline-danger cursor-pointer"
                                        >
                                            Xóa
                                        </Badge>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <CustomPagination onChange={handlePageClick} currentPage={currentPage} totalPages={pageCount} />
                </Row>
            </Wrapper>
        </>
    );
};

export default Brand;
