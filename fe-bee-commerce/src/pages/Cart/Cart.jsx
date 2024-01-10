import React, { useEffect, useState } from "react";
import { LuStore } from "react-icons/lu";
import "./Cart.css";
import { GrFormNext } from "react-icons/gr";
import Wrapper from "~/components/common/Wrapper";
import CheckoutSummary from "~/components/Cart/CheckoutSummary";
import ShoppingCart from "~/components/Cart/ShoppingCart";
import cartApi from "~/api/cartApi";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";

const Cart = () => {
    const { getCartByUserId, deleteCart } = cartApi();
    const dispatch = useDispatch();
    const [listCart, setListCart] = useState([]);

    const [selectedItems, setSelectedItem] = useState([]);

    const handleCheckboxChange = (item) => {
        if (selectedItems.length === 0) {
            setSelectedItem([...selectedItems, item]);
            return;
        }

        const index = selectedItems.findIndex((selectedItem) => selectedItem.id === item.id);
        if (index >= 0) {
            setSelectedItem([...selectedItems.slice(0, index), ...selectedItems.slice(index + 1)]);
        } else {
            setSelectedItem([...selectedItems, item]);
        }
    };

    const handleDeleteCartItem = (id) => {
        dispatch(showLoader());
        deleteCart(id)
            .then(() => {
                NotificationManager.success("Xóa thành công");
                fetchData();
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    };

    // Bilding selectedItems from listCart
    // useEffect(() => {
    //
    //     const newSelectedItems = listCart.reduce((selectedItems, vendor) => {
    //         const newSelectedItems = vendor.products.filter((product) => {
    //             return selectedItems.some((selectedItem) => selectedItem.id === product.id);
    //         })
    //         return [...selectedItems, ...newSelectedItems];
    //     }, []);
    //     setSelectedItem(newSelectedItems);
    // }, [listCart]);
    useEffect(() => {
        const newSelectedItems = [];

        listCart.forEach((vendor) => {
            vendor.products.forEach((product) => {
                const index = selectedItems.findIndex((selectedItem) => selectedItem.id === product.id);
                if (index >= 0) {
                    newSelectedItems.push(product);
                }
            });
        });

        setSelectedItem(newSelectedItems);
    }, [listCart]);

    const fetchData = () => {
        dispatch(showLoader());
        getCartByUserId()
            .then((response) => {
                return response?.data?.reduce((groups, item = {}) => {
                    const { vendorId, vendorName, ...product } = item;

                    const index = groups.findIndex((group) => group.vendorId === vendorId);
                    if (index >= 0) {
                        groups[index].products.push(product);
                    } else {
                        groups.push({
                            vendorId,
                            vendorName,
                            products: [product],
                        });
                    }
                    return groups;
                }, []);
            })
            .then((response) => {
                setListCart(response);
            })
            .catch((error) => {
                NotificationManager.error(error.message, "Lỗi");
            })
            .finally(() => dispatch(hideLoader()));
    };

    useEffect(() => {
        fetchData();
    }, []);

    console.log("listCart", listCart);

    function handleUpdateQuantity(idCart, quantity) {
        console.log("idCart", idCart);
        console.log("quantity", quantity);
        // Tạo một bản sao của state để không làm thay đổi trực tiếp state
        const updatedCart = [...listCart];

        // Duyệt qua từng cửa hàng
        updatedCart.forEach((vendor) => {
            // Duyệt qua từng sản phẩm trong cửa hàng
            vendor.products.forEach((product) => {
                // Kiểm tra nếu idCart trùng khớp
                if (product.id === idCart) {
                    // Cập nhật trường quantity
                    product.quantity = quantity;
                }
            });
        });

        // Cập nhật state mới
        setListCart(updatedCart);
    }

    return (
        <>
            <div className="w-100 max-w-[1200px] mx-auto">
                <Wrapper>
                    <div className="row p-3 mx-2">
                        <div className="col-5 flex  items-center space-x-3 pl-0">
                            <div className="ml-7">Sản phẩm</div>
                        </div>
                        <div className="col-2">Đơn giá</div>
                        <div className="col-2">Số lượng</div>
                        <div className="col-2">Số tiền</div>
                        <div className="col-1">Thao tác</div>
                    </div>
                </Wrapper>
            </div>

            <div className="w-100 max-w-[1200px] mx-auto mt-3">
                {listCart?.map((cart) => {
                    return (
                        <Wrapper key={cart.vendorId}>
                            <div className="p-3 mt-3 mx-2">
                                <div className="row pt-2">
                                    <div
                                        className="col flex  items-center space-x-4 border-b-2 pb-3"
                                        style={{ borderBottom: "1px solid rgba(0,0,0,.09)" }}
                                    >
                                        <div>
                                            <input type="checkbox" name="" id="" />
                                        </div>
                                        <div className="flex  items-center space-x-2 cursor-pointer">
                                            <div className="bg-[#ff424e] rounded-md px-2 text-white text-[13px] font-medium">
                                                Yêu thích
                                            </div>
                                            <LuStore />
                                            <span className="text-[14px] font-medium text-[rgba(0,0,0,.87)]">
                                                {cart.vendorName}
                                            </span>
                                            <GrFormNext className="text-[#a3a3a3]" />
                                        </div>
                                    </div>
                                </div>
                                {/* ------------------END-------------------------------- */}

                                {cart.products.map((item) => {
                                    return (
                                        <ShoppingCart
                                            item={item}
                                            onCheckboxChange={handleCheckboxChange}
                                            onChangeQuantity={handleUpdateQuantity}
                                            selectedItems={selectedItems}
                                            handleDeleteCartItem={handleDeleteCartItem}
                                            updateData={fetchData}
                                        />
                                    );
                                })}
                            </div>
                        </Wrapper>
                    );
                })}
            </div>

            <CheckoutSummary selectedItems={selectedItems} />
        </>
    );
};

export default Cart;
