// noinspection JSValidateTypes

import { FormGroup, InputGroup, Modal } from "react-bootstrap";
import Form from "react-bootstrap/Form";
import { repairCurrency, reverseCurrency, toCurrency } from "~/utils/CurrencyFormatter";
import { useForm } from "react-hook-form";
import { useEffect } from "react";

function UpdatePriceModal({ product: { id, classifies, poster, name }, show, onHide = () => {}, onsubmit = () => {} }) {
    const { register, handleSubmit, reset, getValues, setValue } = useForm();

    const onSubmit = handleSubmit((data) => data);

    function handleChange(value, name) {
        setValue(name, repairCurrency(value, false));
    }

    useEffect(() => {
        reset();
    }, [id]);

    function handleSave() {
        onSubmit().then((data) => console.log(data));
        console.log("Save", id, getValues());
        onsubmit();
    }

    return (
        <Modal show={show}>
            <Modal.Header closeButton>
                <div className="flex flex-col gap-3">
                    <h4 className="inline-block w-100 font-bold text-xl">Cập nhật giá và kho hàng</h4>
                    <div className="flex  gap-3">
                        <img className="border rounded-3 ml-3" width={56} height={56} src={poster} alt={name} />
                        <div className="flex-1">{name}</div>
                    </div>
                </div>
            </Modal.Header>
            <Modal.Body>
                <div className="">
                    {classifies?.map((classify, index) => (
                        <InputGroup className="mb-3" key={index}>
                            <InputGroup.Text className="min-w-[200px]">
                                <span className="max-w-[100px] overflow-clip whitespace-nowrap text-ellipsis">
                                    {classify.classifyGroupValue}
                                </span>
                                ,
                                <span className="max-w-[100px] overflow-clip whitespace-nowrap text-ellipsis">
                                    {classify.classifyValue}
                                </span>
                            </InputGroup.Text>
                            <Form.Control
                                {...register(classify.classifyId + "_price", {
                                    required: true,
                                    onChange: (e) => handleChange(e.target.value, classify.classifyId + "_price"),
                                    min: 0,
                                })}
                                defaultValue={toCurrency(classify.price, false)}
                            />
                            <Form.Control
                                {...register(classify.classifyId + "_inventory", {
                                    required: true,
                                    valueAsNumber: true,
                                    onChange: (e) => handleChange(e.target.value, classify.classifyId + "_inventory"),
                                    min: 0,
                                })}
                                type="number"
                                defaultValue={classify.inventory}
                            />
                        </InputGroup>
                    ))}
                </div>
            </Modal.Body>
            <Modal.Footer>
                <button onClick={handleSave} className="btn btn-primary">
                    Save
                </button>
                <button onClick={() => onHide()} className="btn btn-secondary">
                    Cancel
                </button>
            </Modal.Footer>
        </Modal>
    );
}

export default UpdatePriceModal;
