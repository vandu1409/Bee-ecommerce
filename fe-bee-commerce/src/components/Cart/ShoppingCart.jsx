import React, {useEffect, useRef, useState} from "react";
import QuantityControl from "../common/QuantityControl";
import genuineImage from "../../Images/chinhHang.png";
import { RiDeleteBin6Line } from "react-icons/ri";
import cartApi from "~/api/cartApi";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import { toCurrency } from "~/utils/CurrencyFormatter";

const ShoppingCart = ({ selectedItems, item, onCheckboxChange, handleDeleteCartItem = () => {}, onChangeQuantity = () => {} }) => {
    const [isChecked, setIsChecked] = useState(false);
    const { updateCart, deleteCart } = cartApi();
    const dispatch = useDispatch();

    const [totalPrice, setTotalPrice] = useState(item?.classify?.price * item?.quantity);
    const [quantity, setQuantity] = useState(1);
    const [changed, setChanged] = useState(false);
    const timeoutIdRef = useRef(null);
    const handleCheckboxChange = () => {



        const newItem = { ...item };
        newItem.classify.quantity = quantity;
        onCheckboxChange(newItem);
        setIsChecked(!isChecked);
    };

    // console.log("item", item);

    const handleDelete = (id) => {
        handleDeleteCartItem(id);
    };

    const handleChangeQuantity = (id, quantity) => {
        setChanged(true);
        setQuantity(quantity);
        onChangeQuantity(id, quantity);
        setTotalPrice(item?.classify?.price * quantity);
        if (timeoutIdRef.current) {
            clearTimeout(timeoutIdRef.current);
        }

        // Tạo timeout mới và lưu vào useRef
        timeoutIdRef.current = setTimeout(() => {
            apiUpdate();
        }, 1000); // 1000 milliseconds (1s)
    };

  const apiUpdate = () => {
        const data = {
            id: item.id,
            quantity: quantity,
        };
        updateCart(data)
            .catch(({ data }) => {
                NotificationManager.error(data.message);
            })
            .finally(() => setChanged(false));
    }


    return (
        <div>
            <div className="row pt-4 pb-4" style={{ borderBottom: "1px solid rgba(0,0,0,.09)" }}>
                <div className="col-5 flex  items-center space-x-4">
                    <div>
                        <input type="checkbox" checked={isChecked} onChange={handleCheckboxChange} />
                    </div>
                    <div className="flex space-x-3">
                        <div className="">
                            <img src={item?.product?.poster} alt="" className="w-[80px] h-[80px]" />
                        </div>
                        <div className="">
                            <div>
                                <img src={genuineImage} className="w-[100px] " alt="" />
                            </div>
                            <div>
                                <span className="product-cart-item-title ml-0 mb-0 text-[#27272A] text-[15px] hover:text-[#0B74E5]">
                                    {item?.product?.name}
                                </span>
                            </div>
                            <div className="text-[#a5a1a0] text-[14px]">
                                {item?.classify?.classifyValue} , {item?.classify?.classifyGroupValue}
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-2 flex  items-center">
                    <del className="old-price text-[13px] text-[#a5a1a0] mr-2">₫15.000</del>
                    {toCurrency(item?.classify?.price)}
                </div>
                <div className="col-2 flex  items-center">
                    <QuantityControl
                        isNumber
                        max={item.classify?.inventory}
                        min={1}
                        soluong={item.quantity}
                        id={item.id}
                        handleChangeQuantity={handleChangeQuantity}
                    />
                </div>
                <div className="col-2 flex  items-center">
                    <span className="text-[#ff424e] ml-[12px] font-bold">{toCurrency(totalPrice)}</span>
                </div>
                <div className="col-1 flex  items-center">
                    <button onClick={() => handleDelete(item.id)} className="ml-[15px]">
                        <RiDeleteBin6Line />
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ShoppingCart;
