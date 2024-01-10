import { Button } from "react-bootstrap";
import { NotificationManager } from "react-notifications";
import DepositModal from "~/components/Modal/DepositModal";
import { useEffect, useState } from "react";
import AddressModal from "~/components/Modal/AddressModal";
import Select from "react-select";
import SelectAddressModal from "~/components/Modal/SelectAddressModal";
import addressesApi from "~/api/addressesApi";
import { useDispatch } from "react-redux";
import { hideLoader } from "~/action/Loader.action";
import AddressItem from "~/components/common/AddressItem";

function Demo() {
    const [show, setShow] = useState(false);
    const [showSelect, setShowSelect] = useState(false);
    const { createAddress, getAllAddress } = addressesApi();
    const dispatch = useDispatch();
    const [address, setAddress] = useState([]);
    useEffect(() => {
        getAllAddress()
            .then((response) => {
                dispatch(hideLoader());
                setAddress(response.data);
            })
            .catch(({ error }) => {
                dispatch(hideLoader());
                NotificationManager.error(error?.data?.message, "Lỗi");
            })
            .finally(() => {
                dispatch(hideLoader());
            });
    }, []);

    console.log("address", address);
    return (
        <>
            <Button onClick={() => setShow(true)}>Show</Button>
            <Button onClick={() => setShowSelect(true)}>Show</Button>
            <AddressModal show={show} setter={setShow} address={address} />

            <SelectAddressModal
                required
                show={showSelect}
                onHide={() => setShowSelect(false)}
                createAddress={createAddress}
            />
        </>
    );
}

export default Demo;
