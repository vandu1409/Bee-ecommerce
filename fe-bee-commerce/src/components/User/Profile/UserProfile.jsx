// noinspection JSValidateTypes,JSCheckFunctionSignatures

import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { userDataSelector } from "~/selector/Auth.selector";
import { Button, FormControl } from "react-bootstrap";
import defaultAvatar from "~/Images/avatar.jpg";
import { useForm } from "react-hook-form";
import { NotificationManager } from "react-notifications";
import accountApi from "~/api/accountApi";
import authenticateApi from "~/api/authenticateApi";
import { IoImageOutline } from "react-icons/io5";
import { authenticate } from "~/action/Auth.action";
import SelectAddressModal from "~/components/Modal/SelectAddressModal";
import Badge from "~/components/common/Badge";
import Wrapper from "~/components/common/Wrapper";
import DepositModal from "~/components/Modal/DepositModal";

const UserProfile = () => {
    useEffect(() => {
        document.title = "Thông tin tài khoản";
    }, []);
    const {
        fullname,
        username,
        phoneNumber,
        email,
        avatar,
        role,
        // vendor : { name: vendorName, addressNote: note, fullAddress, street } ,
    } = useSelector(userDataSelector);
    const { updateInfo } = accountApi();
    const [showAddressModal, setShowAddressModal] = useState(false);
    const { authenticateToken } = authenticateApi();
    const dispatch = useDispatch();
    const [selectedImage, setSelectedImage] = useState({ src: avatar || defaultAvatar });
    const {
        register,
        handleSubmit,
        getValues,

        formState: { errors, isDirty, dirtyFields },
    } = useForm({ defaultValues: { fullname } });

    const handleImageChange = (event) => {
        const file = event.target.files[0];
        if (!file) return;
        const src = URL.createObjectURL(file);
        setSelectedImage({ file, src });
    };

    const handleClose = (address) => {
        setShowAddressModal(false);
        if (address) {
            const {
                address: { fullAddress },
            } = getValues();
            console.log(fullAddress);
        }
    };

    const onSubmit = handleSubmit(
        (data) => {
            const formData = new FormData();

            if (dirtyFields.fullname) {
                formData.append("data", JSON.stringify(data));
            }

            if (selectedImage.src !== avatar && selectedImage.src !== defaultAvatar) {
                formData.append("avatar", selectedImage.file);
            }

            if (formData.entries().next().done) {
                NotificationManager.warning("Vui lòng thay đổi thông tin trước khi lưu", "Cảnh báo");
                return;
            }

            updateInfo(formData)
                .then((res) => {
                    NotificationManager.success("Cập nhật thông tin thành công", "Thành công");
                    authenticateToken().then(({ data }) => {
                        dispatch(authenticate(data));
                    });
                })
                .catch(({ data }) => {
                    NotificationManager.error(data.message, "Lỗi");
                });

            // updateInfo(data).then((res) => {
            //     NotificationManager.success("Cập nhật thông tin thành công", "Thành công");
            //     authenticateToken().then(({data}) => {
            //         dispatch(authenticate(data));
            //     });
            // });
        },
        () => {
            console.log("error", errors);
            NotificationManager.error(errors?.fullname?.message, "Lỗi");
        },
    );

    return (
        <Wrapper>
            <div className="col-12 p-3 container-fluid">
                <div className="row pl-6 py-3 border-b-2 border-dark-300 mb-3">
                    <h1 className="text-[18px] text-[#333] font-semibold">Hồ Sơ Của Tôi</h1>
                    <div className="text-[14px] text-[#555]">Quản lý thông tin hồ sơ để bảo mật tài khoản</div>
                </div>
                <div className="row pl-2 ">
                    <div className="col-8">
                        <div className="text-[rgba(85,85,85,.8)] text-[15px] ml-5 row mb-3">
                            <div className="col-3 text-right">Tên đăng nhập</div>
                            <div className="col-9">
                                <span className="text-[#333] font-medium me-2">{username}</span>
                                {role === "CUSTOMER" && <Badge>Khách hàng</Badge>}
                                {role === "VENDOR" && <Badge type={"warning"}>Nhà cung cấp</Badge>}
                                {role === "ADMIN" && <Badge type={"danger"}>Quản trị viên</Badge>}
                            </div>
                        </div>

                        <div className="text-[rgba(85,85,85,.8)] text-[15px] ml-5 row flex items-center mb-3">
                            <div className="col-3 text-right">Tên tài khoản</div>
                            <div className="col-9">
                                <FormControl
                                    className="text-[#333] rounded-sm border-1 border-[#dbdbe1] font-semibold"
                                    isInvalid={!!errors?.fullname}
                                    {...register("fullname", {
                                        required: { value: true, message: "Vui lòng nhập tên" },
                                        minLength: { value: 6, message: "Tên phải có ít nhất 6 ký tự" },
                                        maxLength: { value: 26, message: "Tên không được vượt quá 26 ký tự" },
                                    })}
                                ></FormControl>
                            </div>
                        </div>

                        <div className="text-[rgba(85,85,85,.8)] text-[15px] ml-5 row mb-3">
                            <div className="col-3 text-right">Email</div>
                            <div className="col-9">
                                <span className="text-[#333] font-semibold ">{email}</span>
                            </div>
                        </div>

                        <div className="text-[rgba(85,85,85,.8)] text-[15px] ml-5 row mb-3">
                            <div className="col-3 text-right">Số điện thoại</div>
                            <div className="col-9">
                                <span className="text-[#333] font-semibold ">{phoneNumber}</span>
                            </div>
                        </div>
                    </div>
                    <div className="col-4">
                        <input
                            type="file"
                            id="avatar"
                            className=" hidden"
                            accept="image/*"
                            onChange={handleImageChange}
                        />
                        <div className="flex flex-col items-center">
                            <div className="group/avatar relative border-2 border-dark-400 rounded-full object-fill overflow-hidden h-[112px] w-[112px]">
                                <img className="w-100 h-100 object-fit-contain" src={selectedImage.src} alt="" />
                                <label
                                    htmlFor="avatar"
                                    className="absolute top-100 group-hover/avatar:!top-[63%] ease-in-out duration-300 left-0 w-full h-full bg-dark-950 bg-opacity-40 cursor-pointer flex justify-center pt-3"
                                >
                                    <IoImageOutline className="text-xl text-dark-50" />
                                </label>
                            </div>
                            <div className="text-dark-900 text-center text-xs mt-2">
                                <span>Dụng lượng file tối đa 1 MB</span> <br />
                                <span>Định dạng:.JPEG, .PNG</span>
                            </div>
                        </div>
                    </div>
                    {/*{vendorName && (*/}
                    {/*    <>*/}
                    {/*        <div className="text-lg font-semibold py-2 pl-6">Thông tin cửa hàng</div>*/}
                    {/*        <div className="col-12">*/}
                    {/*            <div className="text-[rgba(85,85,85,.8)] text-[15px] ml-5 row flex items-center mb-3">*/}
                    {/*                <div className="col-2 text-right">Tên của hàng</div>*/}
                    {/*                <div className="col-10">*/}
                    {/*                    <FormControl*/}
                    {/*                        className="text-[#333] rounded-sm border-1 border-[#dbdbe1] font-semibold"*/}
                    {/*                        isInvalid={!!errors?.vendorName}*/}
                    {/*                        {...register("vendorName", {*/}
                    {/*                            required: { value: true, message: "Vui lòng nhập tên cửa hàng" },*/}
                    {/*                            minLength: { value: 6, message: "Tên phải có ít nhất 6 ký tự" },*/}
                    {/*                            maxLength: { value: 26, message: "Tên không được vượt quá 26 ký tự" },*/}
                    {/*                        })}*/}
                    {/*                    ></FormControl>*/}
                    {/*                </div>*/}
                    {/*            </div>*/}
                    {/*        </div>*/}

                    {/*        <div className="col-12">*/}
                    {/*            <div className="text-[rgba(85,85,85,.8)] text-[15px] ml-5 row flex items-center mb-3">*/}
                    {/*                <div className="col-2 text-right">Địa chỉ</div>*/}
                    {/*                <div className="col-10">*/}
                    {/*                    <FormControl*/}
                    {/*                        className=" cursor-pointer text-[#333] rounded-sm border-1 border-[#dbdbe1] font-semibold"*/}
                    {/*                        readOnly={true}*/}
                    {/*                        onClick={() => setShowAddressModal(true)}*/}
                    {/*                       */}
                    {/*                    ></FormControl>*/}
                    {/*                    <SelectAddressModal*/}
                    {/*                        initialAddress={{wardId: 830380, street, note}}*/}
                    {/*                        onHide={handleClose}*/}
                    {/*                        hideOnSubmit={true}*/}
                    {/*                        userInfo={false}*/}
                    {/*                        show={showAddressModal}*/}
                    {/*                    />*/}
                    {/*                </div>*/}
                    {/*            </div>*/}
                    {/*        </div>*/}
                    {/*    </>*/}
                    {/*)}*/}
                    <div className="col-12 text-center">
                        <Button className="w-100 max-w-[130px]" onClick={onSubmit}>
                            Update
                        </Button>
                    </div>
                </div>
            </div>
        </Wrapper>
    );
};
export default UserProfile;
