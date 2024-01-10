import Wrapper from "~/components/common/Wrapper";
import Input from "~/components/common/Input";
import Button from "~/components/common/Button";
import poster11 from "~/Images/poster_11.png";
import {useForm} from "react-hook-form";
import {BsShop} from "react-icons/bs";
import {IoLocationOutline} from "react-icons/io5";
import SelectAddressModal from "~/components/Modal/SelectAddressModal";
import {useEffect, useState} from "react";
import Tippy from "@tippyjs/react";
import vendorApi from "~/api/vendorApi";
import {NotificationManager} from "react-notifications";
import {useDispatch} from "react-redux";
import {hideLoader, showLoader} from "~/action/Loader.action";
import {useNavigate} from "react-router-dom";

function RegisterVendor({children}) {
    const {
        handleSubmit,
        register,
        setValue,
        formState: {errors},
    } = useForm();

    const [selectedAddress, setSelectedAddress] = useState(null);
    const [showSelectAddressModal, setShowSelectAddressModal] = useState(false);
    const {registerVendor} = vendorApi();
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleSelectAddress = (address, detail) => {
        setSelectedAddress({...address, detail});
        setValue("address", getAddressText());
    };

    const onSubmit = (data) => {
    dispatch(showLoader());
        registerVendor({...data, ...selectedAddress})
            .then(() => {
                NotificationManager.success("Đăng ký thành công", "Thành công");
                window.location.href = "/vendor";
            })
            .catch(({data}) => {
                NotificationManager.error(data.message, "Lỗi");
            })
            .finally(() => {
                dispatch(hideLoader());
            }) ;
    };

    const handleHide = () => {
        setShowSelectAddressModal(false);
    };

    function getAddressText() {
        if (!selectedAddress?.detail) return "";
        const {
            street,
            detail: {ward, district, province},
        } = selectedAddress || {};
        return `${street}, ${ward.name}, ${district.name}, ${province.name}`;
    }

    useEffect(() => {
        document.title = "Đăng ký nhà bán hàng";
    }, []);

    return (
        <div className="relative">
            <div className="w-100 max-w-[900px] mx-auto">
                <Wrapper>
                    <div className="row mx-0">
                        <div className="col-md-7 mt-3 px-10 py-4">
                            <div className="text-2xl font-bold mb-3">
                                <h2 className="text-center">Đăng ký trở thành nhà bán hàng</h2>
                            </div>
                            <form onSubmit={handleSubmit(onSubmit)} noValidate>
                                <Input
                                    messageClass="text-red-500"
                                    errorMessage={errors.name?.message}
                                    className="mb-2"
                                    leftIcon={<BsShop/>}
                                    label="Shop's name"
                                    inputRef={register("name", {
                                        required: {
                                            value: true,
                                            message: "Tên shop không được để trống",
                                        },
                                        minLength: {
                                            value: 6,
                                            message: "Tên shop phải có ít nhất 6 ký tự",
                                        },
                                        maxLength: {
                                            value: 30,
                                            message: "Tên shop không được quá 30 ký tự",
                                        },
                                    })}
                                />
                                <Tippy content={getAddressText() || "Bạn chưa chọn địa chỉ"} placement={"right"}>
                                    <div>
                                        <Input
                                            messageClass="text-red-500"
                                            errorMessage={errors.address?.message}
                                            className="mb-3"
                                            leftIcon={<IoLocationOutline/>}
                                            label="Address"
                                            placeholder={"Click để chọn địa chỉ"}
                                            readOnly={true}
                                            onClick={() => setShowSelectAddressModal(true)}
                                            inputRef={register("address", {
                                                required: {
                                                    value: true,
                                                    message: "Bạn chưa chọn địa chỉ",
                                                },
                                            })}
                                        />
                                    </div>
                                </Tippy>
                                <SelectAddressModal
                                    onValid={handleSelectAddress}
                                    onHide={handleHide}
                                    show={showSelectAddressModal}
                                    userInfo={false}
                                />
                                <Button className="mb-3 rounded-lg w-100">Đăng ký ngay</Button>
                            </form>
                        </div>
                        <div className="col-md-5 flex p-0 bg-blue-200 align-items-center">
                            <img src={poster11} alt=""/>
                        </div>
                    </div>
                </Wrapper>
            </div>
        </div>
    );
}

export default RegisterVendor;
