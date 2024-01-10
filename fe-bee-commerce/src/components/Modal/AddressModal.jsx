import { Modal, Button } from "react-bootstrap";
import AddressItem from "../common/AddressItem";

function AddressModal({ setter, show, setShow = () => {}, onSubmit, address }) {
    const handleClose = () => {
        setter(false);
    };

    const handleClick = (item) => {
        setter(false);
        onSubmit(item);
    };

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Địa chỉ nhận hàng</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {address?.map((item) => (
                    <div className="mb-2" key={item.id} onClick={() => handleClick(item)}>
                        <AddressItem address={item} />
                    </div>
                ))}
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Close
                </Button>
            </Modal.Footer>
        </Modal>
    );
}

export default AddressModal;
