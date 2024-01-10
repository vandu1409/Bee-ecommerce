import React, {useEffect, useState} from "react";
import {AiOutlineMinus, AiOutlinePlus} from "react-icons/ai";

const QuantityControl = ({soluong, id, handleChangeQuantity, isNumber, max, min}) => {
    const [quantity, setQuantity] = useState(soluong);

    // const [prevQuantity, setPrevQuantity] = useState(quantity);
    // const [timerId, setTimerId] = useState(null);

    const onChangeQuantity = (value) => {
        if (isNumber) {
            const clearQuantity = parseInt(typeof value === "string"
                ? value.replace(/\D/g, "") || 0
                : value);
            setQuantity(max && clearQuantity > max ? max : min && clearQuantity < min ? min : clearQuantity);
        } else {
            setQuantity(value);
        }
    };

    useEffect(() => {
        // if (quantity !== prevQuantity) {
        // clearTimeout(timerId);
        // const newTimerId = setTimeout(() => {
        handleChangeQuantity(id, quantity);
        // }, 1000);
        // setTimerId(newTimerId);
        // setPrevQuantity(quantity);
        // }

        // return () => {
        // clearTimeout(timerId);
        // };
    }, [quantity]);

    return (
        <div>
            <div className="flex align-content-center ">
                <button
                    className="p-1 bg-blue-50 border-1 border-[#999999]  disabled:bg-dark-100 disabled:border-dark-400 "
                    onClick={() => onChangeQuantity(quantity - 1)}
                    disabled={quantity === 1}
                >
                    <AiOutlineMinus />
                </button>
                <input
                    className="mx-0 w-7 text-center border-[#999999]  border-t border-b focus:outline-none"
                    type="text"
                    value={quantity}
                    onChange={(e) => onChangeQuantity(e.target.value)}
                />
                <button
                    className="p-1 bg-blue-50 border-1 border-[#999999]  disabled:bg-dark-100 disabled:border-dark-400"
                    onClick={() => onChangeQuantity(quantity + 1)}
                    disabled={quantity === max}
                >
                    <AiOutlinePlus />
                </button>
            </div>
        </div>
    );
};

export default QuantityControl;
