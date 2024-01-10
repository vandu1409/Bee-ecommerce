import React, {useContext, useState} from "react";
import { BsTicketPerforated } from "react-icons/bs";
import Wrapper from "../common/Wrapper";
import { Button, Modal } from "react-bootstrap";
import "./Cart.css";
import ListVoucher from "./listVoucher";
import { toCurrency } from "~/utils/CurrencyFormatter";
import AddressItem from "~/components/common/AddressItem";
import cartApi from "~/api/cartApi";
import { NotificationManager } from "react-notifications";
import {CheckoutContext} from "~/pages/Checkout/Checkout";

const CheckoutSummary = ({ selectedItems }) => {
    const [showModal, setShowModal] = useState(false);
    const [voucherValue, setVoucherValue] = useState("");
    const {selectAddressId, setSelectAddressId} = useContext(CheckoutContext);
    const [deliveryFee, setDeliveryFee] = useState(0);
    const [totalDiscount, setTotalDiscount] = useState(0);
    const { checkSameVendor } = cartApi();
    const handleVoucherInputChange = (event) => {
        const inputValue = event.target.value;
        setVoucherValue(inputValue);
    };

    const totalAmount = selectedItems.reduce(
        (total, selectedItem) => total + selectedItem.classify?.price * selectedItem.quantity,
        0,
    );

    function handleCheckout() {
        checkSameVendor(selectedItems.map((item) => item.id))
            .then((res) => {
                const json = JSON.stringify({
                    addressId: selectAddressId,
                    deliveryFee,
                    totalAmount,
                    totalDiscount,
                    selectedItems,
                    vendorName: res.data,
                });
                sessionStorage.setItem("checkoutData", json);
                window.location.href = "/account/checkout";
            })
            .catch(({ data }) => {
                NotificationManager.warning(data?.message);
            });
    }

    return (
        <div className="w-100 max-w-[1200px] mx-auto mt-3">

            <Wrapper className="p-3 mb-4">
                <div className="row mt-2">
                    <div className="col-7"></div>
                    <div className="col-5">
                        <div className="mb-4">
                            <div className="flex gap-2 items-center justify-between space-x-2 mb-1">
                                <span className="text-dark-700 text-sm">Tổng đơn hàng</span>
                                <div className="text-dark-950 text-md">{toCurrency(totalAmount)}</div>
                            </div>
                            <div className="flex gap-2 items-center justify-between space-x-2 mb-1">
                                <span className="text-dark-700 text-sm">Phí vận chuyển</span>
                                <div className="text-dark-950 text-md">{toCurrency(deliveryFee)}</div>
                            </div>
                            <div className="flex gap-3 items-center justify-between space-x-2 mb-1">
                                <span className="text-dark-700 text-sm">Tổng tiền</span>
                                <span className="text-dark-95 text-3xl text-blue-900 font-bold">
                                    {toCurrency(totalAmount + deliveryFee - totalDiscount)}
                                </span>
                            </div>
                        </div>
                        <div className="flex items-center justify-end space-x-2">
                            <Button onClick={handleCheckout} className="w-100 text-center py-3">
                                Mua hàng
                            </Button>
                        </div>
                    </div>
                </div>
            </Wrapper>

            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Body>
                    <div className="row">
                        <h6 style={{ fontWeight: "bold", fontSize: "20px" }}>Chọn ShopBee Voucher</h6>
                    </div>

                    <div className=" flex items-center" style={{ backgroundColor: "#f8f8f8" }}>
                        <div className="input-container">
                            <BsTicketPerforated className="coupon-icon" />
                            <input
                                className="mb-3 input"
                                type="text"
                                placeholder="Nhập mã giảm giá"
                                value={voucherValue}
                                onChange={handleVoucherInputChange}
                            />
                        </div>
                        <div>
                            {voucherValue.trim() === "" ? (
                                <button
                                    disabled
                                    style={{
                                        display: "block",
                                        color: "rgb(196, 196, 207)",
                                        backgroundColor: "rgb(235, 235, 240)",
                                        fontWeight: "500",
                                        borderRadius: "4px",
                                        padding: "7px",
                                        width: "100%",
                                        border: "1px solid rgb(221, 221, 227)",
                                    }}
                                >
                                    Áp dụng
                                </button>
                            ) : (
                                <button
                                    onClick={() => setShowModal(false)}
                                    type="button"
                                    style={{
                                        display: "block",
                                        color: "white",
                                        backgroundColor: "#0b74e5",
                                        fontWeight: "500",
                                        borderRadius: "4px",
                                        padding: "7px",
                                        width: "100%",
                                        border: "1px solid #555",
                                    }}
                                >
                                    Áp dụng
                                </button>
                            )}
                        </div>
                    </div>

                    <div className="scroll-voucher">
                        <span>Mã Giảm giá</span>
                        {[1, 1, 1, 1, 1, 1, 1].map(() => (
                            <ListVoucher />
                        ))}
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
                        onClick={() => setShowModal(false)}
                        type="button"
                        className=""
                        style={{
                            backgroundColor: "#017fff",
                            color: "rgb(255, 255, 255)",
                            borderRadius: "4px",
                            padding: "7px",
                            width: "100px",
                        }}
                    >
                        OK
                    </button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default CheckoutSummary;
