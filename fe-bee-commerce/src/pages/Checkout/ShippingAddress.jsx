import React, { useContext, useEffect, useState } from "react";
import { MdNavigateNext } from "react-icons/md";
import Wrapper from "~/components/common/Wrapper";
import { FaMapMarkerAlt } from "react-icons/fa";
import { CheckoutContext } from "~/pages/Checkout/Checkout";
import addressesApi from "~/api/addressesApi";
import Badge from "~/components/common/Badge";
import SelectAddressModal from "~/components/Modal/SelectAddressModal";
import AddressModal from "~/components/Modal/AddressModal";
import { hideLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import { useDispatch } from "react-redux";
import { Button } from "react-bootstrap";
const ShippingAddress = () => {
    const [showSelectModal, setShowSelectModal] = useState(false);
    const [showCreateModal, setShowCreateModal] = useState(false);
    const { getDetailAddress, getAllAddress, calculateFee } = addressesApi();
    const dispatch = useDispatch();
    const { checkoutData, setCheckoutData, setSelectAddressId } = useContext(CheckoutContext);
    const [address, setAddress] = useState({});

    const [addressUser, setAddressUser] = useState([]);
    const fetchData = () => {
        getAllAddress()
            .then((response) => {
                dispatch(hideLoader());
                setAddressUser(response.data);
            })
            .catch(({ error }) => {
                dispatch(hideLoader());
                NotificationManager.error(error?.data?.message, "Lỗi");
            })
            .finally(() => {
                dispatch(hideLoader());
            });
    };
    useEffect(fetchData, []);
    useEffect(() => {
        if (!checkoutData?.addressId) return;
        getDetailAddress(checkoutData?.addressId).then((res) => {
            setAddress(res.data);
        });
    }, [checkoutData?.addressId]);

    const getClassify = () => {
        return checkoutData.selectedItems.map((item) => item.classifyId);
    };
    const handleSelectAddress = (item) => {
        console.log("Itemmmmmmmmm", item);
        setAddress(item);
        setSelectAddressId(item.id);
        calculateFee({
            address: item.id,
            vendorIds: checkoutData.vendorName.id,
            classifyId: getClassify(),
        }).then((res) => {
            setCheckoutData({
                ...checkoutData,
                deliveryFee: res.data,
            });
        });
    };
    return (
        <div>
            <Wrapper>
                <div className="row p-3 m-0" style={{ backgroundImage: "linear-gradient(90deg,#ebf8ff,#fff)" }}>
                    <div className="flex items-center justify-between">
                        <div className="flex items-center space-x-3">
                            <FaMapMarkerAlt className="w-[20px] h-[20px] text-[#4a90e2]" />
                            <span className="text-[16px] font-bold">Địa chỉ nhận hàng</span>
                        </div>

                        <div
                            onClick={() => setShowSelectModal(true)}
                            className="text-[#4a90e2] cursor-pointer flex items-center space-x-2"
                        >
                            <div>Thay đổi</div>
                            <MdNavigateNext className="" />
                        </div>
                    </div>
                </div>

                <div className="row p-3">
                    <div style={{ fontWeight: "bold" }}>
                        <span
                            style={{
                                borderRight: "1px solid rgba(0,0,0,.09)",
                                paddingRight: "5px",
                            }}
                        >
                            {address?.receiverName}
                        </span>
                        <span>{address?.receiverPhone}</span>
                    </div>
                    <div className="flex space-x-3 items-center">
                        <div>
                            <p className="text-dark-700 space-x-2">
                                <Badge type="success">Địa chỉ</Badge>
                                {address?.detailsAddress}
                            </p>
                        </div>
                    </div>
                </div>
                <div className="p-2">
                    <Button className="w-100" onClick={() => setShowCreateModal(true)}>
                        Thêm địa chỉ mới
                    </Button>
                </div>
            </Wrapper>
            <AddressModal
                onSubmit={handleSelectAddress}
                show={showSelectModal}
                setter={setShowSelectModal}
                address={addressUser}
            />
            <SelectAddressModal
                doCreateAddress={true}
                required
                show={showCreateModal}
                onHide={() => setShowCreateModal(false)}
                onCreatedSuccess={fetchData}
            />
        </div>
    );
};

export default ShippingAddress;
