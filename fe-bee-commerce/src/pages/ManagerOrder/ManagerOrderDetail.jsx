import React, { useEffect, useState } from "react";
import Wrapper from "~/components/common/Wrapper";
import orderApi from "~/api/orderApi";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import { useParams } from "react-router-dom";
import { toCurrency } from "~/utils/CurrencyFormatter";
import { getButtonText } from "~/utils/Helper";
import Badge from "~/components/common/Badge";

const ManagerOrderDetail = () => {
    const { id } = useParams();
    const { getOrderDetail, changeStatus, cancelOrder } = orderApi();
    const dispatch = useDispatch();

    const [listOrderDetail, setListOrderDetail] = useState([]);

    const handleChangeStatus = (idOrder) => {
        dispatch(showLoader());
        changeStatus(idOrder)
            .then((response) => {
                NotificationManager.success(response.data, "Thành công");
            })
            .catch((error) => {
                NotificationManager.error(error.data.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    };

    const handleCancelOrder = (idOrder) => {
        dispatch(showLoader());
        cancelOrder(idOrder)
            .then((response) => {
                NotificationManager.success(response.data, "Thành công");
            })
            .catch((error) => {
                // Access the error message using error.message
                NotificationManager.error(error.data.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    };

    useEffect(() => {
        dispatch(showLoader());
        getOrderDetail(id)
            .then((response) => {
                setListOrderDetail(response.data);
            })
            .catch((error) => {
                NotificationManager.error(error.data.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    }, []);

    const buttonStatus = getButtonText(listOrderDetail);

    return (
        <div className=" mx-auto bg-white ">
            <Wrapper className="p-4">
                <div className="row">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">Tên sản phẩm</th>
                                <th scope="col">Loại hàng</th>
                                <th scope="col">Số lượng</th>
                                <th scope="col">Giá</th>
                                <th scope="col">Tổng tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            {listOrderDetail?.orderDetailsDto?.map((order, index) => (
                                <tr key={index}>
                                    <td>
                                        <div className="flex space-x-3">
                                            <div>
                                                <img
                                                    src={order?.classifyDto.image}
                                                    alt=""
                                                    className="rounded-md w-[100px] h-[70px]"
                                                />
                                            </div>
                                            <div>
                                                <span>{order?.productName}</span>
                                            </div>
                                        </div>
                                    </td>

                                    <td>
                                        <span className="lowercase">{order?.classifyDto?.classifyGroupValue}</span>,
                                        <span className="lowercase">{order?.classifyDto?.classifyValue}</span>
                                    </td>

                                    <td>
                                        <span>{order?.quantity}</span>
                                    </td>

                                    <td>
                                        <span> {toCurrency(order?.classifyDto?.price)}</span>
                                    </td>
                                    <td>
                                        <span></span>
                                        <span>
                                            {toCurrency(
                                                order?.quantity &&
                                                    order?.classifyDto?.price &&
                                                    order.quantity * order.classifyDto.price,
                                            )}
                                        </span>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <div className="flex justify-between">
                        <div className="flex gap-3 mx-2">
                            <span>Trang thái đơn hàng: </span>
                            <Badge>{listOrderDetail?.status}</Badge>
                        </div>
                        <div className="flex items-center space-x-4 justify-end">

                            <div className="flex justify-end">
                                {buttonStatus.isButton ? (
                                    <span
                                        onClick={() => handleChangeStatus(listOrderDetail?.id)}
                                        className="btn btn-warning text-black font-bold uppercase"
                                    >
                                    {buttonStatus.message}
                                </span>
                                ) : (
                                    buttonStatus.message
                                )}
                            </div>
                            {["DELIVERING", "CANCELLED"].includes(listOrderDetail?.status) ? (
                                ""
                            ) : (
                                <div className="flex justify-end">
                                <span
                                    onClick={() => handleCancelOrder(listOrderDetail?.id)}
                                    className="btn btn-danger text-black font-bold uppercase"
                                >
                                    Hủy đơn
                                </span>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </Wrapper>
        </div>
    );
};

export default ManagerOrderDetail;
