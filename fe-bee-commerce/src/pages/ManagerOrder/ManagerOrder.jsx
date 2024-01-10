import React, { useEffect, useState } from "react";
import Wrapper from "~/components/common/Wrapper";
import orderApi from "~/api/orderApi";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import { Link } from "react-router-dom";
import { toCurrency } from "~/utils/CurrencyFormatter";
import Badge from "~/components/common/Badge";
import {Pagination} from "react-bootstrap";
import CustomPagination from "~/components/common/CustomPagination";
import usePagination from "~/hooks/usePagination";
const  ManagerOrder = () => {
    const [showModal, setShowModal] = useState(false);
    const { getAllOrder } = orderApi();
    const dispatch = useDispatch();
    const {currentPage,totalPages,handlePagination,onChange} = usePagination();
    const handleShowModal = () => {
        setShowModal(true);
    };
    const [listOrder, setListOrder] = useState([]);

    useEffect(() => {
        dispatch(showLoader());
        getAllOrder(currentPage)
            .then((response) => {
                setListOrder(response.data);
                handlePagination(response.data);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    }, []);

    const formatDate = (dateArray) => {
        const [year, month, day] = dateArray;
        return `${year}-${month.toString().padStart(2, "0")}-${day.toString().padStart(2, "0")}`;
    };

    console.log(listOrder);

    return (
        <div className=" mx-auto bg-white ">
            <Wrapper className="p-4">
                <div className="row mb-3">
                    <h2 className="font-bold text-[21px]">Danh sách đơn hàng</h2>
                </div>
                <div className="row">
                    <table class="table table-bordered ">
                        <thead className="">
                            <tr>
                                <th scope="col">Mã đơn hàng</th>
                                <th scope="col">Khách hàng</th>
                                <th scope="col">Ngày đặt</th>
                                <th scope="col">Tổng tiền</th>
                                <th scope="col">Phương thức thanh toán</th>
                                <th scope="col">Trạng thái</th>
                                <th scope="col">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            {listOrder.map((order, index) => (
                                <tr key={index}>
                                    <th scope="row">{order.id}</th>
                                    <td>
                                        <span>{order.addressDto.receiverName}</span>
                                    </td>
                                    <td>{formatDate(order.createAt)}</td>

                                    <td> {toCurrency(order?.totalPrice)}</td>

                                    <td>
                                        <div className="w-full">
                                            <Badge type="success">{order.paymentMethod}</Badge>
                                        </div>
                                    </td>

                                    <td>
                                        <Badge type="success">{order.status}</Badge>
                                    </td>

                                    <td>
                                        <Link to={`/account/orderdetail/${order.id}`} className="btn btn-primary">
                                            Chi tiết
                                        </Link>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </Wrapper>
            <div>
                <CustomPagination totalPages={totalPages} currentPage={currentPage} onChange={onChange}/>
            </div>
        </div>
    );
};

export default ManagerOrder;
