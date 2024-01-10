import {useContext, useEffect, useState} from "react";
import {BsTrash3} from "react-icons/bs";
import {IoClose, IoCropOutline} from "react-icons/io5";
import {LuImagePlus} from "react-icons/lu";
import "react-image-crop/src/ReactCrop.scss";
import CustomCropper from "~/components/common/CustomCropper/CustomCropper";
import Input from "~/components/common/Input";
import Wrapper from "../../components/common/Wrapper";
import {CreateProductContext} from "./CreateProduct";
import "./SimpleInfomation.css";
import ModalCategory from "../../components/Modal/ModalCategory";
import attributeApi from "~/api/attributeApi";
import {blobURLtoBase64} from "~/utils/Helper";
import Select from "react-select";
import brandsApi from "~/api/brandsApi";

function SimpleInformation() {
    const {productData, setProductData, setAttributes} = useContext(CreateProductContext);
    const {getAttributesRequired} = attributeApi();
    const {getBrands} = brandsApi();
    const [showModal, setShowModal] = useState(false);
    const [tags, setTags] = useState({value: "", list: []});
    const [brandsOptions, setBrandsOptions] = useState([]);

    const onHideModal = (selectedCategories, text) => {
        setShowModal(false);
        if (selectedCategories && text) {
            setProductData({
                ...productData,
                categories: text,
                categoryId: selectedCategories[selectedCategories.length - 1].id,
            });

            getAttributesRequired(selectedCategories[selectedCategories.length - 1].id).then((res) => {
                setAttributes(res.data.map((i) => ({...i, required: true})));
            });
        }
    };

    // Danh sách các hình ảnh được chọn (đã cắt)
    const [images, setImages] = useState([]);

    // Index của hình ảnh đang được cắt (để hiển thị modal cắt ảnh). Index của state<images>
    const [cropImageIndex, setCropImageIndex] = useState(null);

    // Sử lý chọn file đưa file được chọn vào state images
    const handlerSelectedFile = (event) => {
        const files = event.target.files;
        if (files.length <= 0) return;

        const newImages = [...images];

        for (let i = 0; i < files.length; i++) {
            if (newImages.length >= 9) break;

            const selectedFile = files[i];

            const blobUrl = URL.createObjectURL(selectedFile);

            blobURLtoBase64(blobUrl).then((res) => {
                newImages.push({
                    data: res,
                    fileName: selectedFile.name,
                });
                setImages(newImages);
                event.target.value = null;
            });
        }
    };

    const handlerRemoveImage = (index) => {
        const newImages = [...images];
        newImages.splice(index, 1);
        setImages(newImages);
    };

    const handleCropImage = (dataURL) => {
        setCropImageIndex(null);
        const newImages = [...images];
        newImages[cropImageIndex].data = dataURL;
        // set 9 cái hình thôi
        setImages(newImages);
    };

    const handleChangeSimpleInfo = (e, field) => {
        let value = e.target.value;
        if (field === "name" && value.length > 120) {
            value = value.slice(0, 120);
        }
        setProductData({...productData, [field]: value});
    };


    const handleEditTag = (e) => {
        setTags({...tags, value: e.target.value.trim()});
    };

    const handleAddTag = (e) => {
        if ((e.key === "Enter" || e.key === " ") && tags.value.trim().length > 0) {
            setTags({value: "", list: [...tags.list, tags.value]});
        }
    };

    const handleRemoveTag = (index) => {
        const newTags = [...tags.list];
        newTags.splice(index, 1);
        setTags({...tags, list: newTags});
    };

    useEffect(() => {
        setProductData({...productData, images, tags: [...tags.list]});
    }, [images, tags.list]);

    useEffect(() => {
        getBrands()
            .then((res) => {
                setBrandsOptions(res.data.map((i) => ({value: i.id, label: i.name})));
            })
    }, []);

    function handleChangeBrand(data) {
        setProductData({...productData, brandId: data.value});
    }

    return (
        <Wrapper className="p-4  mb-9">
            <h1 className="text-2xl font-semibold mb-3">Thông tin cơ bản</h1>
            {/* One filed */}
            <div className="flex mb-4">
                <span className="w-[250px] text-right pr-8">
                    Hình ảnh sản phẩm <code>*</code>
                </span>
                <div className="flex flex-wrap gap-y-4">
                    {/* Cắt ảnh */}
                    {cropImageIndex !== null && (
                        <CustomCropper
                            aspect={1}
                            src={images[cropImageIndex].data}
                            onDone={handleCropImage}
                            onCancel={() => setCropImageIndex(null)}
                        />
                    )}
                    {images.map((image, index) => (
                        <div
                            key={index}
                            className="relative w-[80px] h-[80px] border-1 border-dark-200 rounded-xl flex-col mr-3 overflow-hidden"
                        >
                            <img className="w-100 h-100" src={image.data} alt={image.fileName}/>
                            <div
                                className="absolute bottom-0 py-1 w-100 flex justify-around align-items-center bg-dark-500 px-1 bg-opacity-50 text-tw-white">
                                <IoCropOutline className="cursor-pointer" onClick={() => setCropImageIndex(index)}/>
                                <span className="mx-0 text-xs font-thin">|</span>
                                <BsTrash3 className="cursor-pointer" onClick={() => handlerRemoveImage(index)}/>
                            </div>
                        </div>
                    ))}

                    <label
                        className="cursor-pointer w-[80px] h-[80px] border-1 border-dashed rounded-xl flex-col py-2 px-[3px] flex justify-center align-items-center"
                        htmlFor="imagesInput"
                    >
                        <LuImagePlus className="text-2xl"/>
                        <span className="text-mini text-center">Thêm hình ảnh ({images.length}/9)</span>
                    </label>

                    <input
                        multiple
                        disabled={images.length >= 9}
                        onChange={(e) => handlerSelectedFile(e)}
                        id="imagesInput"
                        className="collapse"
                        type="file"
                        name="myImage"
                        accept="image/png, image/gif, image/jpeg"
                    />
                </div>
            </div>
            <div className="flex mb-4">
                <span className="w-[250px] text-right pr-8">
                    Tên sản phẩm <code>*</code>
                </span>
                <div className="flex-1">
                    <Input
                        value={productData.name}
                        placeholder="Nhập tên sản phẩm"
                        rightIcon={<span className="text-sm h-100 leading-7">{productData.name.length}/120</span>}
                        className="mb-0"
                        onChange={(e) => {
                            handleChangeSimpleInfo(e, "name");
                        }}
                    />
                </div>
            </div>
            <div className="flex mb-4">
                <span className="w-[250px] text-right pr-8">
                    Thương hiệu <code>*</code>
                </span>
                <div className="flex-1">
                    <Select onChange={handleChangeBrand} options={brandsOptions}/>
                </div>
            </div>
            <div className="flex mb-4">
                <span className="w-[250px] text-right pr-8">
                    Ngành Hàng<code>*</code>
                </span>
                <div className="flex-1">
                    <Input
                        value={productData.categories}
                        onClick={() => setShowModal(true)}
                        className="mb-0 cursor-pointer"
                        placeholder="Click để chọn ngành hàng"
                        readOnly
                    />
                </div>
            </div>
            <div className="flex mb-4">
                <span className="w-[250px] text-right pr-8">
                    Mô tả sản phẩm<code>*</code>
                </span>
                <div className="flex-1">
                    <Input
                        value={productData.description}
                        textArea={{rows: 7}}
                        className="mb-0"
                        onChange={(e) => {
                            handleChangeSimpleInfo(e, "description");
                        }}
                    />
                </div>
            </div>
            <div className="flex mb-4">
                <span className="w-[250px] text-right pr-8">Thẻ tìm kiếm</span>
                <div className="flex-1">
                    <Input
                        value={tags.value}
                        placeholder="Nhập thẻ tìm kiếm (ví dụ: quần áo, giày dép, túi xách...)"
                        className="mb-0"
                        onChange={(e) => handleEditTag(e)}
                        onKeyDown={(e) => handleAddTag(e)}
                    />
                    <div className="pt-1 flex">
                        {tags.list.map((tag, index) => (
                            <span
                                key={index}
                                className="flex align-items-center mr-1 text-mini font-medium  p-1 rounded-md bg-green-300 text-dark-800"
                            >
                                #{tag}
                                <IoClose onClick={() => handleRemoveTag(index)} className="cursor-pointer ml-1"/>
                            </span>
                        ))}
                    </div>
                </div>
            </div>
            <ModalCategory showModal={showModal} onHideModal={onHideModal}/>
        </Wrapper>
    );
}

export default SimpleInformation;
