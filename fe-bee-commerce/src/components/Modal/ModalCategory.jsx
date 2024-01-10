import React, { useEffect, useState } from "react";
import { Modal } from "react-bootstrap";
import { CiSearch } from "react-icons/ci";
import { GrNext } from "react-icons/gr";
import categoriesApi from "~/api/categoriesApi";
import Input from "~/components/common/Input";

const ModalCategory = ({ showModal, onHideModal, selectedCategoryId, handleSetTextCategory, isClearn }) => {
    const [selectedCategories, setSelectedCategories] = useState([]);
    const { getFirstCategories, getChildrenCategories, getCategoryById } = categoriesApi();

    const [categoryBlock, setCategoryBlock] = useState([]);

    useEffect(() => {
        if (isClearn) {
            setSelectedCategories([]);
            setFormattedCategories("");
            handleUpdateCategoryBlock();
        }
    }, [isClearn]);

    const handleUpdateCategoryBlock = () => {
        getFirstCategories()
            .then((response) => {
                setCategoryBlock([response.data]);
                console.log("categoryBlock: ", response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    // Lấy ra danh sach categories của level 1
    useEffect(() => {
        getFirstCategories()
            .then((response) => {
                setCategoryBlock([response.data]);
                console.log("categoryBlock: ", response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    }, []);

    useEffect(() => {
        const newCategoryBlock = [...categoryBlock];
        getCategoryById(selectedCategoryId)
            .then((response) => {
                const selectedCategory = response.data;
                const newSelectedCategories = [selectedCategory];

                if (selectedCategory.parentCategories && selectedCategory.parentCategories.length > 0) {
                    selectedCategory.parentCategories.forEach((parentCategory) => {
                        newSelectedCategories.unshift(parentCategory);
                    });
                }
                setSelectedCategories(newSelectedCategories);
                setFormattedCategories(newSelectedCategories.map((category) => category.name).join(" > "));
                handleSetTextCategory(newSelectedCategories.map((category) => category.name).join(" > "));
                const categoriesToProcess = newSelectedCategories.slice(0, -1);

                console.log("categoriesToProcess", categoriesToProcess);
                categoriesToProcess.forEach((category) => {
                    getChildrenCategories(category.id)
                        .then((response) => {
                            const currentLevelCategories = response.data;
                            newCategoryBlock[category.level] = currentLevelCategories;
                        })
                        .catch((error) => {
                            console.error(error);
                        });
                    setCategoryBlock(newCategoryBlock);
                });
            })
            .catch((error) => {
                console.error(error);
            });
    }, [selectedCategoryId]);

    const handleCategoryClick = ({ id, parentId, level, name, posterUrl, hasChildren }) => {
        const newCategoryBlock = [...categoryBlock];

        if (!hasChildren) {
            newCategoryBlock.splice(level);
            setCategoryBlock(newCategoryBlock);
        } else {
            getChildrenCategories(id)
                .then((response) => {
                    // Lấy ra danh sách categories của level hiện tại
                    const currentLevelCategories = response.data;
                    newCategoryBlock[level] = currentLevelCategories;
                    newCategoryBlock.splice(level + 1);
                })
                .catch((error) => {
                    newCategoryBlock.splice(level);
                    console.error(error);
                })
                .finally(() => {
                    // Xóa các category block có index lớn hơn level hiện tại
                    setCategoryBlock(newCategoryBlock);
                });
        }

        const newSelectedCategories = selectedCategories;
        newSelectedCategories[level - 1] = { id, parentId, level, name, posterUrl };

        // Xóa các selected categories có index lớn hơn level hiện tại
        newSelectedCategories.splice(level);

        setSelectedCategories(newSelectedCategories);
        setFormattedCategories(selectedCategories.map((category) => category.name).join(" > "));
    };

    const [formattedCategories, setFormattedCategories] = useState("");

    // Truyền selectedCategories về component cha
    const handleHideModal = () => {
        onHideModal(selectedCategories, formattedCategories);
    };

    return (
        <div>
            <Modal size="xl" show={showModal}>
                <Modal.Body>
                    <div className="row mb-3">
                        <h6 style={{ fontWeight: "bold", fontSize: "20px" }}>Chỉnh sửa ngành hàng</h6>
                    </div>
                    <div className="container" style={{ backgroundColor: "#f6f6f6", minHeight: "400px" }}>
                        <div className="flex items-center justify-between mb-3 pt-2">
                            <div style={{ backgroundColor: "white", width: "300px", height: "35px" }}>
                                <Input leftIcon={<CiSearch />} placeholder={"Vui lòng nhập tối thiểu 1 ký tự"} />
                            </div>
                            <div>
                                <span style={{ color: "#2673dd", fontWeight: "400" }}>Chọn ngành hàng chính xác</span>
                            </div>
                        </div>

                        <div className="grid grid-cols-4">
                            {categoryBlock.map((categories, index) => (
                                <ul key={index} className={`scroll-item-data`}>
                                    {categories &&
                                        categories.map((category) => (
                                            <li
                                                className={`category-item cursor-pointer hover:bg-dark-100
                                                    ${selectedCategories[index]?.id === category.id && "bg-blue-100"}
                                                    `}
                                                key={category.id}
                                                onClick={() => handleCategoryClick(category)}
                                            >
                                                <span> {category.name}</span>
                                                <div>{category.hasChildren && <GrNext />}</div>
                                            </li>
                                        ))}
                                </ul>
                            ))}
                        </div>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <div className="flex items-center justify-between w-full">
                        <div>
                            <span className="text-dark-400 font-bold">Đã chọn :</span>
                            <span className="text-[17px] font-bold">{formattedCategories}</span>
                        </div>
                        <div className="flex space-x-2">
                            <button
                                onClick={handleHideModal}
                                type="button"
                                className="font-semibold rounded-md p-2 w-24 border border-gray-300"
                            >
                                Trở về
                            </button>
                            <button
                                onClick={handleHideModal}
                                type="button"
                                className="bg-blue-500 text-white rounded-md p-2 w-24"
                            >
                                OK
                            </button>
                        </div>
                    </div>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default ModalCategory;
