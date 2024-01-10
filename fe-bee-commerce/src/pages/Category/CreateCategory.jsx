import React, { useEffect, useState } from "react";
import { NotificationManager } from "react-notifications";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import categoriesApi from "~/api/categoriesApi";
import Wrapper from "~/components/common/Wrapper";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import { Alert, Button } from "react-bootstrap";

import { useForm } from "react-hook-form";
import ModalCategory from "~/components/Modal/ModalCategory";
import SelectImage from "~/components/common/SelectImage";
import { Link, useNavigate, useParams } from "react-router-dom";

const CreateCategory = () => {
    const dispatch = useDispatch();
    const [show, setShow] = useState(false);
    const [selectCategories, setSelectCategories] = useState({ data: [], text: "" });
    const [formData, setFormData] = useState({
        name: "",
    });

    const [parentId, setParentId] = useState(-1);
    const [posterUrl, setPosterUrl] = useState(null);
    const [image, setImage] = useState(null);
    const [isReset, setIsReset] = useState(false);
    const { id } = useParams();
    const [textCategory, setTextCategory] = useState("");

    const { createCategory, getCategoryById, UpdateCategory } = categoriesApi();

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm();

    const navigate = useNavigate();

    const resetData = () => {
        setSelectCategories({ data: [], text: "" });
        setIsReset(true);
        setImage(null);
        setFormData({
            name: "",
        });
        setTextCategory("");
    };

    useEffect(() => {
        if (selectCategories.data.length > 0) {
            const lastIndex = selectCategories.data.length - 1;
            const lastCategory = selectCategories.data[lastIndex];
            const parentId = lastCategory.id;
            setParentId(parentId);
        }
    }, [selectCategories]);

    const handleHide = (data, text) => {
        setShow(false);
        setSelectCategories({ data, text });
    };

    const handleSetTextCategory = (text) => {
        setTextCategory(text);
    };

    console.log("parentId", parentId);

    const onSubmit = handleSubmit((data) => {
        const formData = new FormData();
        formData.append("name", data.name);
        formData.append("parentId", parentId ? parentId : -1);
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
                    resetData();
                });
        } else {
            dispatch(showLoader());

            createCategory(formData)
                .then((response) => {
                    dispatch(hideLoader());
                    NotificationManager.success(response.data, "Thành công");
                    navigate("/admin/categories/list");
                })
                .catch((error) => {
                    dispatch(hideLoader());
                    NotificationManager.error(error.message, "Lỗi");
                })
                .finally(() => {
                    dispatch(hideLoader());
                    resetData();
                });
        }
    });

    useEffect(() => {
        if (id) {
            dispatch(showLoader());
            getCategoryById(id)
                .then((response) => {
                    dispatch(hideLoader());
                    setParentId(response.data.parentId);
                    setPosterUrl(response.data.posterUrl);

                    setFormData({
                        name: response.data.name,
                    });
                })
                .catch((error) => {
                    dispatch(hideLoader());
                    NotificationManager.error(error.message, "Lỗi");
                })
                .finally(() => {
                    dispatch(hideLoader());
                });
        }
    }, [id]);

    return (
        <div className=" mx-auto bg-white ">
            <Wrapper className="py-6 px-4">
                <h2 className="text-2xl font-bold mb-4">{id ? "Cập nhật ngành hàng" : "Thêm mới ngành hàng"}</h2>
                <div className="row">
                    <div className="col-8">
                        <InputGroup className="mb-2" onClick={() => setShow(true)}>
                            <InputGroup.Text className="min-w-[200px]" id="inputGroup-sizing-default">
                                Chọn ngành hàng cha
                            </InputGroup.Text>
                            <Form.Control
                                placeholder="Click để chọn ngành hàng cha"
                                className="cursor-pointer focus:outline-none focus:ring-0"
                                readOnly
                                value={id ? textCategory : selectCategories.text}
                            ></Form.Control>
                        </InputGroup>
                        <Form.Text className="!mb-3 block">
                            <Alert className="py-2">
                                Nếu không chọn ngành hàng cha thì ngành hàng mới sẽ là ngành hàng cấp 1
                            </Alert>
                        </Form.Text>
                        <InputGroup className="mb-3">
                            <InputGroup.Text className="min-w-[200px]" id="inputGroup-sizing-default">
                                Tên ngành hàng
                            </InputGroup.Text>
                            <Form.Control
                                {...register("name", {
                                    required: {
                                        value: true,
                                        message: "Tên ngành hàng là bắt buộc",
                                    },
                                    minLength: {
                                        value: 5,
                                        message: "Tên ngành hàng phải có ít nhất 5 ký tự",
                                    },
                                    maxLength: {
                                        value: 100,
                                        message: "Tên ngành hàng không được vượt quá 100 ký tự",
                                    },
                                })}
                                placeholder="Nhập tên ngành hàng"
                                className="cursor-pointer focus:outline-none focus:ring-0"
                                value={formData.name}
                                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                            />
                        </InputGroup>
                        <Form.Text className="!mb-3 block">
                            {errors.name && (
                                <Alert variant="danger" className="py-2">
                                    {errors?.name?.message}
                                </Alert>
                            )}
                        </Form.Text>
                    </div>
                    <div className="col-4 flex items-center justify-center obj">
                        <SelectImage
                            title="Chọn ảnh đại diện cho ngành hàng"
                            imgClassName="w-[120px] h-[120px] border-1 border-dashed object-fit-contain rounded-full flex-col"
                            onChange={(e) => setImage(e.target.files[0])}
                            replace
                            imageUrl={id && posterUrl}
                            isReset={isReset}
                        />
                    </div>
                </div>
                <div className="flex space-x-2 mt-2">
                    <div>
                        <Button onClick={onSubmit} variant="primary" className="">
                            {id ? "Cập nhật" : "Tạo mới"}
                        </Button>
                        <Button variant="secondary" onClick={() => resetData()}>
                            Làm mới
                        </Button>
                        <Link to={"/admin/categories/lists"} className="btn btn-primary">
                            Danh sách ngành hàng
                        </Link>
                    </div>
                </div>
            </Wrapper>
            <ModalCategory
                showModal={show}
                onHideModal={handleHide}
                selectedCategoryId={parentId}
                handleSetTextCategory={handleSetTextCategory}
                isClearn={isReset}
            />
        </div>
    );
};

export default CreateCategory;
