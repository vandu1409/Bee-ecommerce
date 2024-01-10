// noinspection JSCheckFunctionSignatures

import { Button, Form as FormUi, InputGroup, Modal } from "react-bootstrap";
import { useEffect, useState } from "react";
import addressesApi from "~/api/addressesApi";
import { useForm } from "react-hook-form";
import { useDispatch, useSelector } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import { NotificationManager } from "react-notifications";
import { userDataSelector } from "~/selector/Auth.selector";

function SelectAddressModal({
    children,
    show,
    onShow = () => {},
    onHide = () => {},
    onValid = () => {},
    onInvalid = () => {},
    hideOnSubmit = true,
    required = false,
    userInfo = true,
    readOnly = false,
    initialAddress = {
        provinceId: undefined,
        districtId: undefined,
        wardId: undefined,
        street: "",
        receiverName: "",
        receiverPhone: "",
        note: "",
    },
    doCreateAddress = false,
    onCreatedSuccess = () => {},
    ...rest
}) {
    const [provinces, setProvinces] = useState([]);
    const [districts, setDistricts] = useState([]);
    const [wards, setWards] = useState([]);
    const { getProvince, getDistrict, getWard, loadInitialAddress, createAddress } = addressesApi();
    const [detail, setDetail] = useState(null);

    const {
        setValue,
        watch,
        register,
        handleSubmit: formHandleSubmit,
        formState: { errors, isValid },
    } = useForm({
        mode: "onBlur",
        reValidateMode: "onChange",
        defaultValues: initialAddress,
    });

    const provinceSelected = watch("provinceId");
    const districtSelected = watch("districtId");
    const wardSelected = watch("wardId");

    const loadProvinces = () => {
        return getProvince()
            .then((response) => {
                setProvinces(response.data);
                setWards([]);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    const loadDistricts = (provinceId) => {
        return getDistrict(provinceId)
            .then((response) => {
                setDistricts(response.data);
                setWards([]);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    const loadWards = (districtId) => {
        return getWard(districtId)
            .then((response) => {
                setWards(response.data);
            })
            .catch((error) => {
                console.error(error);
            });
    };

    const loadAddress = async () => {
        return loadInitialAddress(initialAddress?.wardId).then((data) => {
            initialAddress = {
                ...initialAddress,
                provinceId: data.provinceId,
                districtId: data.districtId,
                wardId: data.wardId,
            };

            console.log("Init trong hàm", initialAddress);
        });
    };

    const onSubmit = formHandleSubmit((data) => onValid(data, detail), onInvalid);

    async function initSelectedOptions() {
        if (!initialAddress.wardId) return;

        await loadAddress();

        console.log("init sau sài", initialAddress);

        setValue("provinceId", initialAddress.provinceId);

        try {
            await loadDistricts(initialAddress.provinceId);
            if (initialAddress.districtId) {
                setValue("districtId", initialAddress.districtId);
                await loadWards(initialAddress.districtId);
                if (initialAddress.wardId) {
                    setValue("wardId", initialAddress.wardId);
                }
            }
        } catch (error) {
            console.error("Error loading data:", error);
        }
    }

    function handleClose() {
        onHide();
    }

    const dispatch = useDispatch();

    function handleSubmit() {
        onSubmit().then(() => {
            if (required && !isValid) {
                return;
            }
            hideOnSubmit && handleClose();
            if (doCreateAddress) {
                const formData = watch(); // Lấy tất cả dữ liệu từ form

                dispatch(showLoader());

                createAddress(formData)
                    .then((response) => {
                        dispatch(hideLoader());
                        NotificationManager.success(response.data, "Thành công");
                        onCreatedSuccess();
                    })
                    .catch(({ error }) => {
                        dispatch(hideLoader());
                        NotificationManager.error(error?.data?.message, "Lỗi");
                    })
                    .finally(() => {
                        dispatch(hideLoader());
                    });
                console.log("Detail", detail);
                console.log("formData", formData);
            }
        });
    }

    useEffect(() => {
        loadProvinces();
    }, []);

    useEffect(() => {
        if (!show && provinces.length === 0) return;
        initSelectedOptions().then(() => console.log("Init selected options"));
    }, [provinces]);

    useEffect(() => {
        const newDetail = {};

        if (provinceSelected) {
            newDetail.province = provinces.find((province) => province.id === Number(provinceSelected));
        }

        if (districtSelected) {
            newDetail.district = districts.find((district) => district.id === Number(districtSelected));
        }

        if (wardSelected) {
            newDetail.ward = wards.find((ward) => ward.id === Number(wardSelected));
        }

        setDetail(newDetail);
    }, [provinceSelected, districtSelected, wardSelected]);

    // noinspection JSValidateTypes
    return (
        <Modal show={show}>
            <Modal.Header closeButton>
                <Modal.Title>Chọn địa chỉ</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {userInfo && (
                    <>
                        <div className="mb-3">
                            <InputGroup size="sm">
                                <InputGroup.Text className="min-w-[130px]" id="receiver">
                                    Người nhận
                                </InputGroup.Text>
                                <FormUi.Control
                                    {...register("receiverName", {
                                        required: {
                                            value: true,
                                            message: "Tên người nhận không được để trống",
                                        },
                                    })}
                                    readOnly={readOnly}
                                    type="text"
                                    placeholder="Tên người nhận"
                                    aria-label="Người nhận"
                                />
                            </InputGroup>
                            <FormUi.Text className="text-danger">{errors?.receiverName?.message}</FormUi.Text>
                        </div>
                        <div className="mb-3">
                            <InputGroup size="sm">
                                <InputGroup.Text className="min-w-[130px]" id="phone">
                                    Số điện thoại
                                </InputGroup.Text>
                                <FormUi.Control
                                    {...register("receiverPhone", {
                                        required: { value: true, message: "Số điện thoại không được để trống" },
                                        pattern: {
                                            value: /((09|03|07|08|05)+([0-9]{8})\b)/g,
                                            message: "Số điện thoại không hợp lệ",
                                        },
                                    })}
                                    type="text"
                                    readOnly={readOnly}
                                    placeholder="Số điện thoại người nhận"
                                    aria-label="Số điện thoại"
                                />
                            </InputGroup>
                            <FormUi.Text className="text-danger">{errors?.receiverPhone?.message}</FormUi.Text>
                        </div>
                    </>
                )}
                <div className="mb-3">
                    <InputGroup size="sm">
                        <InputGroup.Text className="min-w-[130px]" id="receiver">
                            Tỉnh/thành phố
                        </InputGroup.Text>
                        <FormUi.Select
                            {...register("provinceId", {
                                required: {
                                    value: true,
                                    message: "Tỉnh/thành phố không được để trống",
                                },
                                onChange: (e) => loadDistricts(e.target.value),
                            })}
                            readOnly={readOnly}
                            onClick={() => provinces.length === 0 && loadProvinces()}
                            size="sm"
                        >
                            <option value={""}>Chọn tỉnh/thành phố</option>
                            {provinces.map(({ id, name }) => (
                                <option value={id}>{name}</option>
                            ))}
                        </FormUi.Select>
                    </InputGroup>
                    <FormUi.Text className="text-danger">{errors?.provinceId?.message}</FormUi.Text>
                </div>
                <div className="mb-3">
                    <InputGroup size="sm">
                        <InputGroup.Text className="min-w-[130px]" id="receiver">
                            Quận/huyện
                        </InputGroup.Text>
                        <FormUi.Select
                            {...register("districtId", {
                                required: {
                                    value: true,
                                    message: "Quận/huyện không được để trống",
                                },
                                onChange: (e) => loadWards(e.target.value),
                            })}
                            onClick={() =>
                                districts.length === 0 && districtSelected && loadDistricts(provinceSelected)
                            }
                            readOnly={readOnly}
                            size="sm"
                        >
                            <option value={""}>Chọn quận/huyện</option>
                            {districts.map((province) => (
                                <option value={province.id}>{province.name}</option>
                            ))}
                        </FormUi.Select>
                    </InputGroup>
                    <FormUi.Text className="text-danger">{errors?.districtId?.message}</FormUi.Text>
                </div>
                <div className="mb-3">
                    <InputGroup size="sm">
                        <InputGroup.Text className="min-w-[130px]" id="receiver">
                            Phường/xã
                        </InputGroup.Text>
                        <FormUi.Select
                            {...register("wardId", {
                                required: {
                                    value: true,
                                    message: "Phường/xã không được để trống",
                                },
                            })}
                            onClick={() => wards.length === 0 && districtSelected && loadWards(districtSelected)}
                            readOnly={readOnly}
                            size="sm"
                        >
                            <option value={""}>Chọn phường/xã</option>
                            {wards.map((ward) => (
                                <option value={ward.id}>{ward.name}</option>
                            ))}
                        </FormUi.Select>
                    </InputGroup>
                    <FormUi.Text className="text-danger">{errors?.wardId?.message}</FormUi.Text>
                </div>
                <div className="mb-3">
                    <InputGroup size="sm">
                        <InputGroup.Text className="min-w-[130px]" id="street">
                            Số nhà, tên đường
                        </InputGroup.Text>
                        <FormUi.Control
                            {...register("street", {
                                required: {
                                    value: true,
                                    message: "Địa chỉ cụ thể không được để trống",
                                },
                            })}
                            readOnly={readOnly}
                            type="text"
                            placeholder="Địa chỉ cụ thể"
                            aria-label="Số nhà, tên đường"
                        />
                    </InputGroup>
                    <FormUi.Text className="text-danger">{errors?.street?.message}</FormUi.Text>
                </div>
                <div className="mb-3">
                    <InputGroup size="sm">
                        <InputGroup.Text className="min-w-[130px] align-items-start" id="street">
                            Ghi chú
                        </InputGroup.Text>
                        <FormUi.Control
                            {...register("note")}
                            readOnly={readOnly}
                            as="textarea"
                            type="text"
                            rows={2}
                            placeholder="Ghi chú"
                            aria-label="Số nhà, tên đường"
                        />
                    </InputGroup>
                    <FormUi.Text className="text-danger">{errors?.street?.message}</FormUi.Text>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={handleClose}>Close</Button>
                <Button variant="success" onClick={handleSubmit}>
                    Save Changes
                </Button>
            </Modal.Footer>
        </Modal>
    );
}

export default SelectAddressModal;
