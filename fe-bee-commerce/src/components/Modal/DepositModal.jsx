// noinspection JSValidateTypes,JSCheckFunctionSignatures

import { Button, FormControl, InputGroup, Modal } from "react-bootstrap";
import { useState } from "react";
import { reverseCurrency, toCurrency } from "~/utils/CurrencyFormatter";
import Form from "react-bootstrap/Form";
import { NotificationManager } from "react-notifications";
import InputGroupText from "react-bootstrap/InputGroupText";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { useDispatch } from "react-redux";
import accountApi from "~/api/accountApi";
import { IoCloseOutline } from "react-icons/io5";

function AmountButton({ amount, setAmount = () => {}, children }) {
    return (
        <Button
            variant={`${amount === toCurrency(children, false) ? "primary" : "outline-primary"}`}
            onClick={() => setAmount(children)}
        >
            {toCurrency(children)}
        </Button>
    );
}

function DepositModal({ setter = () =>{} , show}) {
    const dispatch = useDispatch();
    const { deposit } = accountApi();
    const [amount, setAmount] = useState(0);

    function handleChangeAmount(e) {
        const value = (e.target && reverseCurrency(e.target?.value)) || e;
        if (value < 0 && value > 100000000) {
            NotificationManager.warning("Số tiền nạp không hợp lệ");
            return;
        }
        setAmount(toCurrency(value, false));
    }

    function handlePay() {
        dispatch(showLoader());
        deposit(amount)
            .then((res) => {
                dispatch(showLoader());
                // window.location.href(res.data, "_blank");
                window.open(res.data, "_self");
            })
            .catch((err) => {
                dispatch(hideLoader());
                NotificationManager.error("Nạp tiền thất bại");
            });
    }

    return (
        <Modal show={show} centered>
            <Modal.Header>
                <div className="flex justify-between w-100">
                    <div className="font-bold text-xl">Nạp tiền</div>
                    <div onClick={() => setter(false)} className="p-2 text-lg cursor-pointer rounded-lg hover:bg-red-100">
                        <IoCloseOutline />
                    </div>
                </div>
            </Modal.Header>
            <Modal.Body>
                <div className="px-4">
                    <div className="font-semibold text-sm text-gray-500">Chọn số tiền nạp vào tài khoản</div>
                    <div className="py-3 px-1 flex flex-wrap gap-2">
                        <AmountButton amount={amount} setAmount={handleChangeAmount}>
                            10000
                        </AmountButton>
                        <AmountButton amount={amount} setAmount={handleChangeAmount}>
                            20000
                        </AmountButton>
                        <AmountButton amount={amount} setAmount={handleChangeAmount}>
                            50000
                        </AmountButton>
                        <AmountButton amount={amount} setAmount={handleChangeAmount}>
                            100000
                        </AmountButton>
                        <AmountButton amount={amount} setAmount={handleChangeAmount}>
                            200000
                        </AmountButton>
                        <AmountButton amount={amount} setAmount={handleChangeAmount}>
                            300000
                        </AmountButton>
                        <AmountButton amount={amount} setAmount={handleChangeAmount}>
                            500000
                        </AmountButton>
                    </div>
                    <InputGroup className="mb-4">
                        <FormControl value={amount} onChange={handleChangeAmount} />
                        <InputGroupText>đ</InputGroupText>
                    </InputGroup>
                    <Button onClick={handlePay} className="w-100">
                        {" "}
                        Thanh toán{" "}
                    </Button>
                </div>
            </Modal.Body>
        </Modal>
    );
}

export default DepositModal;
