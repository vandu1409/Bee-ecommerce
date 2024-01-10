import React, { useEffect, useState } from "react";
import "./InfoProduct.scss";
import { BsCartPlus, BsFacebook, BsMessenger, BsSuitHeart, BsSuitHeartFill } from "react-icons/bs";
import { AiFillTwitterCircle, AiOutlineMinus, AiOutlinePlus } from "react-icons/ai";
import Wrapper from "../../common/Wrapper";
import Star from "../../common/Star";
import { toCurrency } from "../../../utils/CurrencyFormatter";
import Button from "../../common/Button";
import cartApi from "~/api/cartApi";
import { useDispatch, useSelector } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import { userDataSelector } from "~/selector/Auth.selector";
import { Link, useNavigate, useParams } from "react-router-dom";
import CarauselImage from "./CarauselImage";
import WishlistApi from "~/api/wishlistApi";

function ProductDetail({ selectedId, discount = 0, product }) {
    const navigate = useNavigate();
    const { addToCart } = cartApi();
    const dispatch = useDispatch();

    const {
        name,
        images,
        poster,
        classifies,
        classifyName,
        classifyGroupName,
        rootPrice,
        liked = false,
        star,
        inStock,
        likedCount: likedCountProp = 100,
        sold,
        voteCount,
    } = product;

    const userData = useSelector(userDataSelector);
    const [quantity, setQuantity] = useState(1); // Số lượng sản phẩm để thêm vào giỏ hàng

    const [isLiked, setIsLiked] = useState(liked); // Trạng thái yêu thích sản phẩm của sản phẩm
    const [selectedClassify, setSelectedClassify] = useState([]); // Danh sách phân loại đã chọn của sản phẩm
    const [likeCount, setLikeCount] = useState(likedCountProp); // Số lượng yêu thích của sản phẩm
    const [classifyId, setClassifyId] = useState("");
    const [priceByClassify, setPriceByClassify] = useState("");

    const [selectedClassifyGroupValue, setSelectedClassifyGroupValue] = useState("");
    const [selectedClassifyValue, setSelectedClassifyValue] = useState("");

    const seenClassifyGroupValues = new Set();
    const seenClassifyValues = new Set();

    const [currentUpImg, setCurrentUpImg] = useState();

    const defaultRootPrice = rootPrice ?? 0;

    const defaultStar = star ?? 3;

    const defaultSold = sold ?? 100;

    const defaultVote = voteCount ?? 100;

    const defaultInstock = inStock ?? 100;

    const { createWishlist } = WishlistApi();

    const handleAddToCart = () => {
        if (selectedClassifyGroupValue === "" && selectedClassifyValue === "") {
            NotificationManager.error("Vui lòng chọn loại sản phẩm", "Lỗi");

            return;
        }
        if (!userData) {
            // Lưu trữ đường dẫn hiện tại vào localStorage
            localStorage.setItem("previousPath", window.location.pathname);
            navigate("/login");

            return;
        }
        const data = {
            classtyId: classifyId,
            quantity: quantity,
        };

        dispatch(showLoader());
        addToCart(data)
            .then((response) => {
                dispatch(hideLoader());
                NotificationManager.success(response.data, "Thêm giỏ hàng thành công");
            })
            .catch((error) => {
                dispatch(hideLoader());
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => {
                dispatch(hideLoader());
                setSelectedClassifyGroupValue("");
                setSelectedClassifyValue("");
                setQuantity(1);
            });
    };

    const onChangeQuantity = (value) => {
        setQuantity(value > 0 ? (value <= defaultInstock ? value : defaultInstock) : 1);
    };

    const handleLike = () => {
       
        dispatch(showLoader());
        createWishlist(selectedId)
            .then(({message, result}) => {
                NotificationManager.success(message, "Thông báo");
                setLikeCount(result ? likeCount - 1 : likeCount + 1);
                setIsLiked(!result);
            })
            .catch((error) => {
                NotificationManager.error(error?.data?.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    };

    const handleSelectClassifyGroupValue = (classifyGroupValue) => {
        setSelectedClassifyGroupValue(classifyGroupValue);
    };

    const handleSelectClassifyValue = (classifyValue) => {
        setSelectedClassifyValue(classifyValue);
    };

    useEffect(() => {
        if (classifies) {
            const filteredClassifies = classifies.filter(
                (classify) =>
                    classify.classifyGroupValue === selectedClassifyGroupValue &&
                    classify.classifyValue === selectedClassifyValue,
            );

            console.log("filteredClassifies", filteredClassifies);

            if (filteredClassifies.length > 0) {
                setClassifyId(filteredClassifies[0].classifyId);
                setPriceByClassify(filteredClassifies[0].price);
            }
        }
    }, [selectedClassifyGroupValue, selectedClassifyValue]);

    return (
        <Wrapper className="mb-5 py-4">
            <div className="flex px-4">
                {/* Ảnh của sản phẩm */}
                <div className="col-md-5 pr-3">
                    <div className="img-up mb-3 border-1 border-[rgba(0,0,0,.09)] h-[450px] ">
                        <img className="rounded-lg h-full w-full" src={currentUpImg || poster} alt="" />
                    </div>
                    <div className="flex justify-center flex-wrap h-[125px] border-1 border-[rgba(0,0,0,.09)]  overflow-hidden pt-[10px] relative">
                        {images && images.length < 4 ? (
                            images.map((imgUrl, index) => (
                                <div
                                    key={index}
                                    className="w-[110px] h-[110px] mb-[10px] mx-2   transition-all duration-250 hover:-translate-y-[3px]"
                                >
                                    <img
                                        src={imgUrl}
                                        alt=""
                                        onClick={() => setCurrentUpImg(imgUrl)}
                                        className={`w-full h-full border-solid rounded-lg border-blue-500  ${
                                            currentUpImg === imgUrl ? "border-2" : ""
                                        }`}
                                    />
                                </div>
                            ))
                        ) : (
                            <CarauselImage
                                images={images}
                                currentUpImg={currentUpImg}
                                setCurrentUpImg={setCurrentUpImg}
                            />
                        )}
                    </div>
                    <div
                        className="share-like d-flex justify-items-center align-items-center gap-4 "
                        style={{ marginLeft: "100px", marginTop: "20px", marginBottom: "20px" }}
                    >
                        <div className="">
                            <div className="share-productdetail  d-flex justify-items-center align-items-center">
                                <span style={{ fontSize: "15px", fontWeight: "bold" }}> Chia sẽ:</span>{" "}
                                <BsMessenger color="#0384ff" size={"30px"} style={{ paddingLeft: "5px" }} />{" "}
                                <BsFacebook color="#3b5999" size={"30px"} style={{ paddingLeft: "5px" }} />{" "}
                                <AiFillTwitterCircle style={{ paddingLeft: "5px" }} color="#10c2ff" size={"30px"} />
                            </div>
                        </div>
                        <div className="">
                            <div className="like-productDetail  d-flex justify-items-center align-items-center">
                                <span className="text-3xl text-red-500 mr-2" onClick={() => handleLike()}>
                                    {isLiked ? <BsSuitHeart /> : <BsSuitHeartFill />}
                                </span>
                                <span style={{ fontWeight: "600" }}>Đã thích </span>
                            </div>
                        </div>
                    </div>
                </div>
                {/* Hết ảnh sản phẩm */}
                <div className="col-md-7 pl-5">
                    <div className="info-basic-product">
                        <div className="name-product " style={{ paddingBottom: "10px" }}>
                            <span className="font-bold text-[12px] me-2 bg-red-500 text-tw-white px-2 py-1 rounded-md">
                                Yêu thích
                            </span>
                            <span className="font-bold text-2xl">{name}</span>
                        </div>
                        {/* Lượt bán và đánh giá */}
                        <div className="productdetail-rating-Evaluate flex align-items-center gap-2 mb-3">
                            <Star className={`!text-lg`} point={defaultStar} />
                            <span>({defaultVote})</span>
                            <span>|</span>
                            <span>Đã bán {defaultSold}</span>
                        </div>

                        {/* Giá sản phẩm */}
                        <div className="ml-3 flex gap-2 align-items-end">
                            <del className="font-medium text-dark-400">{toCurrency(defaultRootPrice)}</del>
                            {priceByClassify ? (
                                <span className="text-4xl font-bold text-blue-600">{toCurrency(priceByClassify)}</span>
                            ) : (
                                <span className="text-4xl font-bold text-blue-600">{toCurrency(defaultRootPrice)}</span>
                            )}

                            <span className="bg-red-500 ml-3 py-[px] rounded-full px-2 text-tw-white text-sm font-medium">
                                - {discount}%
                            </span>
                        </div>

                        <div className="pl-6">
                            <div
                                className="code-sale-shop d-flex justify-items-center gap-5 align-items-center mb-3"
                                style={{ marginTop: "20px" }}
                            >
                                <span
                                    style={{
                                        marginBottom: "0px",
                                        color: "#757575",
                                        width: "100px",
                                    }}
                                >
                                    Mã Giảm Giá Của Shop
                                </span>
                                <div className="flex overflow-hidden h-[25px] w-1/2 flex-wrap">
                                    {[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1].map((item, index) => (
                                        <span className="mr-4 text-[13px] mb-[5px] bg-blue-200 text-blue-700 px-2 py-[3px]">
                                            Giảm {item}k
                                        </span>
                                    ))}
                                </div>
                            </div>

                            {/* Phân loai sản phẩm */}
                            <div className="d-flex justify-items-center gap-5 align-items-start mb-3">
                                <span
                                    style={{
                                        marginBottom: "0px",
                                        color: "#757575",
                                        width: "100px",
                                    }}
                                >
                                    {classifyGroupName}
                                </span>
                                <div className="flex flex-wrap w-1/2">
                                    {classifies &&
                                        classifies.map((classify, index) => {
                                            if (!seenClassifyGroupValues.has(classify.classifyGroupValue)) {
                                                seenClassifyGroupValues.add(classify.classifyGroupValue);
                                                return (
                                                    <Button
                                                        className={`mr-4 rounded-md px-4 py-1 mb-2 hover:!bg-blue-100 hover:!text-blue-600 disabled:!bg-blue-300 ${
                                                            selectedClassifyGroupValue === classify.classifyGroupValue
                                                                ? "!bg-blue-100"
                                                                : ""
                                                        }`}
                                                        outline
                                                        onClick={() =>
                                                            handleSelectClassifyGroupValue(classify.classifyGroupValue)
                                                        }
                                                        disabled={
                                                            classify.classifyId ===
                                                            selectedClassify[classify.classifyGroupId]
                                                        }
                                                        key={index}
                                                    >
                                                        {classify.classifyGroupValue}
                                                    </Button>
                                                );
                                            }
                                            return null;
                                        })}
                                </div>
                            </div>

                            <div className="d-flex justify-items-center gap-5 align-items-start mb-3">
                                <span
                                    style={{
                                        marginBottom: "0px",
                                        color: "#757575",
                                        width: "100px",
                                    }}
                                >
                                    {classifyName}
                                </span>
                                <div className="flex flex-wrap w-1/2">
                                    {classifies &&
                                        classifies.map((classify, index) => {
                                            if (!seenClassifyValues.has(classify.classifyValue)) {
                                                seenClassifyValues.add(classify.classifyValue);
                                                return (
                                                    <Button
                                                        className={`mr-4 rounded-md px-4 py-1 mb-2 hover:!bg-blue-100 hover:!text-blue-600 disabled:!bg-blue-300 ${
                                                            selectedClassifyValue === classify.classifyValue
                                                                ? "!bg-blue-100"
                                                                : ""
                                                        }`}
                                                        outline
                                                        onClick={() =>
                                                            handleSelectClassifyValue(classify.classifyValue)
                                                        }
                                                        disabled={
                                                            classify.classifyId ===
                                                            selectedClassify[classify.classifyGroupId]
                                                        }
                                                        key={index}
                                                    >
                                                        {classify.classifyValue}
                                                    </Button>
                                                );
                                            }
                                            return null;
                                        })}
                                </div>
                            </div>

                            <div
                                className="size-productdetail d-flex justify-items-center gap-5 align-items-center"
                                style={{ marginTop: "25px" }}
                            >
                                <span
                                    style={{
                                        marginBottom: "0px",
                                        color: "#757575",
                                        width: "100px",
                                    }}
                                >
                                    Số lượng
                                </span>
                                <div className="flex align-content-center justify-center">
                                    <button
                                        className="p-2 bg-blue-50 border-1 border-blue-400 rounded-l-md disabled:bg-dark-100 disabled:border-dark-400 "
                                        onClick={() => onChangeQuantity(quantity - 1)}
                                        disabled={quantity === 1}
                                    >
                                        <AiOutlineMinus />
                                    </button>
                                    <input
                                        className="mx-0 w-10 text-center border-blue-400 border-t border-b focus:outline-none"
                                        type="text"
                                        value={quantity}
                                        onChange={(e) => onChangeQuantity(e.target.value)}
                                    />
                                    <button
                                        className="p-2 bg-blue-50 border-1 border-blue-400 rounded-r-md disabled:bg-dark-100 disabled:border-dark-400"
                                        onClick={() => onChangeQuantity(quantity + 1)}
                                        disabled={quantity >= defaultInstock}
                                    >
                                        <AiOutlinePlus />
                                    </button>
                                </div>
                                <span className="">{defaultInstock} sản phẩm có sẵn</span>
                            </div>

                            <div
                                className=" d-flex justify-items-center gap-3 align-items-center"
                                style={{ marginTop: "25px" }}
                            >
                                <Button
                                    outline
                                    onClick={handleAddToCart}
                                    className="rounded-md !bg-blue-50 flex justify-center align-items-center"
                                    style={{
                                        width: "200px",
                                    }}
                                >
                                    <BsCartPlus className="text-lg" /> <span className="ml-2">Thêm giỏ hàng</span>
                                </Button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </Wrapper>
    );
}

export default ProductDetail;
