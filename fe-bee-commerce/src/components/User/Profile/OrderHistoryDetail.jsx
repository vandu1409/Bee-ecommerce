import React, { useEffect, useState } from "react";
import { GrPrevious } from "react-icons/gr";
import { RxUpdate } from "react-icons/rx";
import { MdOutlinePayments } from "react-icons/md";
import { FaRegStar } from "react-icons/fa";
import "./OrderDetail.css";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import OrderApi from "~/api/orderApi";
import { NotificationManager } from "react-notifications";
import { toCurrency } from "~/utils/CurrencyFormatter";
import { Space, Rate } from "antd";
import { Input } from "antd";
import { blobURLtoBase64 } from "~/utils/Helper";
import { LuImagePlus } from "react-icons/lu";

import { dataURLtoFile } from "~/utils/Helper";

import { Modal } from "react-bootstrap";
import feelbackApi from "~/api/feelbackApi";

const OrderHistoryDetail = () => {
    const { id } = useParams();
    const [orderDetail, setOrderDetail] = useState();

    const { orderHistoryUserLoggedDetail, completeOrder } = OrderApi();

    useEffect(() => {
        dispatch(showLoader());
        orderHistoryUserLoggedDetail(id)
            .then((response) => {
                setOrderDetail(response.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    }, [id]);

    console.log("orderDetail", orderDetail);

    const { createFeedback } = feelbackApi();

    const handleFeedback = (id) => {
        setShowModal(true);
        setIdOrderDetail(id);
    };

    const [idOrderDetail, setIdOrderDetail] = useState();

    const desc = ["Quá tệ", "Tệ", "Bình thường", "Tốt", "Quá tuyệt"];

    const [value, setValue] = useState(3);

    const [showModal, setShowModal] = useState(false);

    const { TextArea } = Input;

    const [content, setContent] = useState("");

    const dispatch = useDispatch();

    // Hàm xử lý khi nội dung thay đổi
    const handleContentChange = (e) => {
        setContent(e.target.value);
    };

    const [images, setImages] = useState([]);

    console.log("image", images);
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
            });
        }
    };
    const navigate = useNavigate();
    console.log("idOrderDetail", idOrderDetail);

    const HandleCreateFeedback = () => {
        const formData = new FormData();
        formData.append("idOrderDetail", idOrderDetail);
        formData.append("rating", value);
        formData.append("comment", content);
        images
            ?.map(({ data, fileName }) => dataURLtoFile(data, fileName))
            .forEach((img) => formData.append("feedbackImages", img));

        dispatch(showLoader());
        createFeedback(formData)
            .then((response) => {
                NotificationManager.success(response.data, "Thành công");
                navigate("/account/ordered");
            })
            .catch((error) => {
                NotificationManager.error(error.data?.message, "Lỗi");
                console.error(error);
            })
            .finally(() => dispatch(hideLoader()));
    };

    function handleReceived(id) {
        console.log("346238746132sadijksfhasdkfjadjkf4392047",id)
        completeOrder(id)
            .then((response) => {
                NotificationManager.success("Xát nhận đã nhận hàng", "Thành công");
                navigate("/account/orderedetail/" + id);
            })
            .catch((error) => {
                NotificationManager.error(error.data?.message, "Lỗi");
                console.error(error);
            });
    }

    return (
        <>
            <div className=" bg-white">
                <div className=" p-3 flex items-center justify-between">
                    <div className="text-[rgba(0,0,0,.54)] flex items-center space-x-2 cursor-pointer">
                        <GrPrevious />
                        <div>
                            <Link to="/account/ordered">
                                <span>TRỞ LẠI</span>{" "}
                            </Link>
                        </div>
                    </div>
                    <div className="flex items-center space-x-3 text-[14px]">
                        <div>
                            MÃ ĐƠN HÀNG : <span className="text-[#7490b2] font-bold">230701T7U1SCV9</span>
                        </div>
                        <div>|</div>
                        <div className="text-[#ee4d2d]">
                            {orderDetail?.status === "PENDING"
                                ? "CHỜ XÁC NHẬN"
                                : orderDetail?.status === "CONFIRMED"
                                ? "ĐÃ XÁC NHẬN"
                                : orderDetail?.status === "DELIVERING"
                                ? "ĐANG GIAO HÀNG"
                                : orderDetail?.status === "COMPLETED"
                                ? "ĐÃ GIAO HÀNG"
                                : orderDetail?.status === "CANCELLED"
                                ? "ĐÃ HỦY"
                                : orderDetail?.status === "RETURNED"
                                ? "ĐÃ TRẢ HÀNG"
                                : ""}
                        </div>
                    </div>
                </div>

                <div className="mt-3 mb-5 flex items-center justify-center">
                    <div className="flex items-center w-[250px]">
                        <div className="flex-col justify-center items-center w-[150px] text-center">
                            <div className="flex items-center justify-center">
                                <RxUpdate
                                    className={`flex items-center justify-center ${
                                        orderDetail?.status === "PENDING" ||
                                        orderDetail?.status === "CONFIRMED" ||
                                        orderDetail?.status === "COMPLETED" // ? "bg-green-500 text-white p-2 w-
                                            ? "bg-green-500 text-white p-2 w-12 h-12 rounded-full"
                                            : "border-[#adabb2] border-1 text-[#adabb2] p-2 w-12 h-12 rounded-full"
                                    }`}
                                />
                            </div>
                            <span>Chờ xác nhận</span>
                        </div>
                        <div className="flex-grow border-t-2 border-[#adabb2] mb-3"></div>
                    </div>

                    <div className="flex items-center w-[250px]">
                        <div className="flex-col justify-center items-center w-[150px] text-center">
                            <div className="flex items-center justify-center">
                                <MdOutlinePayments
                                    className={`flex items-center justify-center ${
                                        orderDetail?.status === "CONFIRMED" || orderDetail?.status === "COMPLETED"
                                            ? "bg-green-500 text-white p-2 w-12 h-12 rounded-full"
                                            : "border-[#adabb2] border-1 text-[#adabb2] p-2 w-12 h-12 rounded-full"
                                    }`}
                                />
                            </div>
                            <span>Đã xác nhận</span>
                        </div>
                        <div className="flex-grow border-t-2 border-[#adabb2] mb-3"></div>
                    </div>

                    <div className="flex items-center w-[250px]">
                        <div className="flex-col justify-center items-center w-[150px] text-center">
                            <div className="flex items-center justify-center">
                                <FaRegStar
                                    className={`flex items-center justify-center ${
                                        orderDetail?.status === "COMPLETED"
                                            ? "bg-green-500 text-white p-2 w-12 h-12 rounded-full"
                                            : "border-[#adabb2] border-1 text-[#adabb2] p-2 w-12 h-12 rounded-full"
                                    }`}
                                />
                            </div>
                            <span>Đã giao</span>
                        </div>
                    </div>
                </div>

                <div className="row aaa m-0"></div>

                <div className="row border-b-2 pb-4 m-0  border-t-2 border-[rgba(0,0,0,.09)] p-1 pt-2">
                    <div className="col border-r-2 border-[#e2e8f0]">
                        <div className="row pt- mb-2  m-0">
                            <div className=" mb-3 ">
                                <span className="text-[20px] font-semibold ">Địa chỉ nhận hàng</span>
                            </div>

                            <div className="mb-2">
                                <span className="text-[17px] font-medium">{orderDetail?.addressDto?.receiverName}</span>
                            </div>

                            <div className="text-[#808089] text-[15px]">
                                <span>(+84) {orderDetail?.addressDto?.receiverPhone}</span>
                            </div>

                            <div className="text-[#808089] text-[15px]">
                                <span>{orderDetail?.addressDto?.detailsAddress}</span>
                            </div>
                        </div>
                    </div>
                    <div className="col border-r-2 border-[#e2e8f0]">
                        <div className="row pt- mb-2  m-0 ">
                            <div className=" mb-3 ">
                                <span className="text-[20px] font-semibold ">Hình thức vận chuyển</span>
                            </div>

                            <div className="mb-2">
                                <span className="text-[17px] ">Giao hàng nhanh</span>
                            </div>
                        </div>
                    </div>
                    <div className="col">
                        <div className="row pt- mb-2  m-0">
                            <div className=" mb-3 ">
                                <span className="text-[20px] font-semibold ">Hình thức thanh toán</span>
                            </div>

                            <div className="text-[17px]">
                                <span>
                                    {orderDetail?.paymentMethod === "COD"
                                        ? "Thanh toán khi nhận hàng"
                                        : orderDetail?.paymentMethod === "VNPAY"
                                        ? "Thanh toán online"
                                        : orderDetail?.paymentMethod}
                                </span>
                            </div>
                        </div>
                    </div>
                </div>

                <>
                    <div className="row py-2 m-0">
                        <div className="flex space-x-3 items-center">
                            <div className="bg-[#ef5233] text-white text-[13px] font-medium rounded-sm">
                                <span className="p-2"> Yêu thích</span>
                            </div>
                            <div className="font-bold">{orderDetail?.shopName}</div>
                        </div>
                    </div>
                    {orderDetail?.orderDetailsDto.map((order) => (
                        <div className="flex items-center p-2 pl-1 border-b-2 border-[rgba(0,0,0,.09)] justify-between">
                            <div className="flex">
                                <div className="w-[100px] h-[80px] border-1 border-[#e7e8ea]">
                                    <img src={order.classifyDto.image} alt="" className="w-full h-full" />
                                </div>
                                <div className=" pl-3">
                                    <div>
                                        <span className="text-[rgba(0,0,0,.87)] text-[16px] font-bold">
                                            {order.productName}
                                        </span>
                                    </div>
                                    <div className="text-[rgba(0,0,0,.54)]">
                                        Phần loại hàng {order.classifyDto.classifyGroupValue},{" "}
                                        {order.classifyDto.classifyValue}
                                    </div>

                                    <div className="flex items-center justify-between">
                                        <div>x{order.classifyDto.quantity}</div>
                                        <div>
                                            <del className="text-[14px] text-[#000] opacity-40 mr-2"></del>
                                        </div>
                                    </div>

                                    <span className="text-[#ee4d2d]">Giá: {toCurrency(order.classifyDto.price)}</span>
                                </div>
                            </div>
                            {!order.hasFeedback && orderDetail?.status === "COMPLETED" && (
                                <div className="">
                                    <button
                                        onClick={() => handleFeedback(order.id)}
                                        className="bg-[#ee4d2d] py-[8px] px-[30px] rounded-sm text-white"
                                    >
                                        Đánh giá
                                    </button>
                                </div>
                            )}
                        </div>
                    ))}
                </>

                <div className="row p-3  border-1 border-[rgba(0,0,0,.09)] bg-[#fffcf5] m-0">
                    <div className="col-6"></div>
                    <div className="col-2"></div>
                    <div className="col-4">
                        <div className=" flex justify-between items-center mb-2">
                            <div className="col-7">
                                <span className="text-[17px] font-bold">Tổng tiền hàng : </span>
                            </div>
                            <div className="col-5 text-right">
                                <span className="text-[17px] font-bold">{toCurrency(orderDetail?.totalPrice)}</span>
                            </div>
                        </div>

                        <div className=" flex justify-between items-center mb-2">
                            <div className="col-7">
                                <span className="text-[17px] font-bold">Giảm giá : </span>
                            </div>
                            <div className="col-5 text-right">
                                <span className="text-[17px]  font-bold ">- {toCurrency(orderDetail?.discount)}</span>
                            </div>
                        </div>

                        <div className=" flex justify-between items-center mb-2">
                            <div className="col-7">
                                <span className="text-[17px] font-bold">Phí vận chuyển : </span>
                            </div>
                            <div className="col-5 text-right">
                                <span className="text-[17px] font-bold ">{toCurrency(orderDetail?.shippingFee)}</span>
                            </div>
                        </div>

                        <div className=" flex justify-between items-center mb-2">
                            <div className="col-7">
                                <span className="text-[17px] font-bold">Thành tiền :</span>
                            </div>
                            <div className="col-5 text-right">
                                <span className="text-[19px] text-[#ee4d2d]  font-bold ">
                                    {toCurrency(
                                        orderDetail?.totalPrice - orderDetail?.discount + orderDetail?.shippingFee,
                                    )}
                                </span>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="row p-3">
                    <div className="row">
                        <div className="flex items-center justify-between p-2">
                            <span>Cảm ơn bạn đã mua sắm tại Shop Bee!</span>

                            <div>
                                {orderDetail?.status === "DELIVERING" && (
                                    <button
                                        onClick={(e) =>handleReceived(orderDetail?.id)}
                                        className="w-[200px] px-[8px] py-[10px] bg-[#ee4d2d] text-[white]"
                                    >Xát nhận đã nhận hàng</button>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Modal show={showModal} onHide={() => setShowModal(false)} size="lg">
                <Modal.Body>
                    <div className="row mb-2">
                        <h6 style={{ fontWeight: "bold", fontSize: "20px" }}>Đánh giá sản phẩm</h6>
                    </div>

                    <div className="row">
                        <div className="flex mb-4">
                            <span className="w-[200px]  pr-4">
                                Hình ảnh sản phẩm <code>*</code>
                            </span>
                            <div className="flex flex-wrap gap-y-4">
                                {images.map((image, index) => (
                                    <div
                                        key={index}
                                        className="relative w-[80px] h-[80px] border-1 border-dark-200 rounded-xl flex-col mr-3 overflow-hidden"
                                    >
                                        <img className="w-100 h-100" src={image.data} alt={image.fileName} />
                                    </div>
                                ))}

                                <label
                                    className="cursor-pointer w-[80px] h-[80px] border-1 border-dashed rounded-xl flex-col py-2 px-[3px] flex justify-center align-items-center"
                                    htmlFor="imagesInput"
                                >
                                    <LuImagePlus className="text-2xl" />
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
                    </div>

                    <div className="row pt-3">
                        <div className="flex items-center space-x-5">
                            <span> Chọn đánh giá sản phẩm :</span>
                            <Space>
                                <Rate tooltips={desc} onChange={setValue} value={value} style={{ fontSize: "30px" }} />
                                {value ? <span className="text-[#fadb14] font-bold">{desc[value - 1]}</span> : ""}
                            </Space>
                        </div>
                    </div>

                    <div className="row mt-2 p-3">
                        <div>
                            <TextArea
                                onChange={handleContentChange}
                                rows={6}
                                placeholder="Nhập nội dung đánh giá"
                                maxLength={300}
                            />
                        </div>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <button
                        onClick={() => setShowModal(false)}
                        type="button"
                        className=""
                        style={{
                            color: "#fff;",
                            fontWeight: "500",
                            borderRadius: "4px",
                            padding: "7px",
                            width: "100px",
                            border: "1px solid #555",
                        }}
                    >
                        Trở về
                    </button>

                    <button
                        onClick={() => HandleCreateFeedback()}
                        className="bg-[#ff424e] text-white py-[8px] px-[60px] rounded-sm"
                    >
                        Hoàn thành
                    </button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default OrderHistoryDetail;
