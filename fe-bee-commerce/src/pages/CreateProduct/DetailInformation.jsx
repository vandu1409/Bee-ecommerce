import Tippy from "@tippyjs/react/headless";
import {useContext, useEffect, useState} from "react";
import {Modal} from "react-bootstrap";
import {AiOutlineDelete} from "react-icons/ai";
import {BsPlusCircleDotted} from "react-icons/bs";
import "react-image-crop/src/ReactCrop.scss";
import {useDebounce} from "use-debounce";
import attributeApi from "~/api/attributeApi";
import Button from "~/components/common/Button";
import Input from "~/components/common/Input";
import Wrapper from "../../components/common/Wrapper";
import {CreateProductContext} from "./CreateProduct";

const mockSearchAttributes = [
    { id: 1, name: "Chất liệu 1" },
    { id: 2, name: "Chất liệu 2" },
    { id: 3, name: "Chất liệu 3" },
    { id: 4, name: "Chất liệu 4 " },
    { id: 5, name: "Chất liệu 5" },
];

function DetailInformation({}) {
    const { attributes, setAttributes } = useContext(CreateProductContext);
    const { searchAttributes } = attributeApi();
    const [attrFound, setAttrFound] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [keySearch, setKeySearch] = useState("");
    const [keySearchDebounce] = useDebounce(keySearch, 500);

    const [simpleInfo, setSimpleInfo] = useState({
        name: "",
        categories: "",
        description: "",
    });

    const handleChangeSimpleInfo = (e, field) => {
        let value = e.target.value;
        if (field === "name" && value.length > 120) {
            value = value.slice(0, 120);
        }
        setSimpleInfo({ ...simpleInfo, [field]: value });
    };

    useEffect(() => {
        if (keySearchDebounce === "") {
            setAttrFound([]);
            return;
        }
        searchAttributes(keySearchDebounce).then((res) => {
            setAttrFound(res.data);
        });
    }, [keySearchDebounce]);

    const handleNewAttribute = (data) => {
        const newAttributes = [...attributes];
        newAttributes.push(data);
        setAttributes(newAttributes);
        setShowModal(false);
        setKeySearch("");
    };

    const handleRemoveAttribute = (index, required) => {
        if (required) {
            alert("Không thể xóa thuộc tính bắt buộc");
            return;
        }
        const newAttributes = [...attributes];
        newAttributes.splice(index, 1);
        setAttributes(newAttributes);
    };

    const handleChangeAttribute = (e, index) => {
        const newAttributes = [...attributes];
        newAttributes[index].value = e.target.value;
        setAttributes(newAttributes);
    };

    const handlePressKeySearch = (e) => {
        let value = e.target.value;
        value = value.length > 25 ? value.slice(0, 25) : value;
        setKeySearch(value);
    };

    const onSubmit = (data) => {
        console.log(data);
    };

    return (
        <Wrapper className="p-4 pb-0 mb-9">
            <h1 className="text-2xl font-semibold mb-3">Thông tin chi tiết</h1>
            <div>
                <div className="flex mb-4">
                    <div className="flex-1">
                        <div className="grid grid-cols-2 gap-2">
                            {attributes.map((attribute, index) => (
                                <div key={index} className="flex mb-4">
                                    <span className="w-[180px] text-right pr-8">
                                        {attribute.name} {attribute.required && <code>*</code>}
                                    </span>
                                    <div className="flex-1">
                                        <Input
                                            value={attribute.value}
                                            className="mb-0"
                                            rightIcon={
                                                <AiOutlineDelete
                                                    onClick={() =>
                                                        handleRemoveAttribute(
                                                            index,
                                                            attribute.required,
                                                        )
                                                    }
                                                />
                                            }
                                            onChange={(e) => {
                                                handleChangeAttribute(e, index);
                                            }}
                                        />
                                    </div>
                                </div>
                            ))}
                            <div>
                                <div className="flex mb-4">
                                    <span className="w-[180px] text-right pr-8"></span>
                                    <div className="flex-1">
                                        <Modal show={showModal} centered>
                                            <Modal.Header className="px-3">
                                                <h5>Thêm thuộc tích cho sản phẩm</h5>
                                            </Modal.Header>
                                            <Modal.Body>
                                                <div>
                                                    <Tippy
                                                        interactive={true}
                                                        placement={"bottom-start"}
                                                        visible={!!keySearch}
                                                        render={(attrs) => (
                                                            <div {...attrs}>
                                                                <Wrapper className="mb-3 flex flex-col px-3 w-100 py-2 mt-3">
                                                                    <span
                                                                        onClick={() =>
                                                                            handleNewAttribute(
                                                                                { name: keySearch },
                                                                                setShowModal,
                                                                            )
                                                                        }
                                                                        className="py-1 px-4 hover:bg-dark-100 rounded-lg cursor-pointer "
                                                                    >
                                                                        {keySearch}
                                                                    </span>
                                                                    {attrFound.map(
                                                                        (value, i) => (
                                                                            <span
                                                                                onClick={() =>
                                                                                    handleNewAttribute(
                                                                                        value,
                                                                                        setShowModal,
                                                                                    )
                                                                                }
                                                                                key={i}
                                                                                className="py-1 px-4 hover:bg-dark-100 rounded-lg cursor-pointer"
                                                                            >
                                                                                {value.name}
                                                                            </span>
                                                                        ),
                                                                    )}
                                                                </Wrapper>
                                                            </div>
                                                        )}
                                                    >
                                                        <div>
                                                            <Input
                                                                value={keySearch}
                                                                onChange={handlePressKeySearch}
                                                                onKeyUp={(e) =>
                                                                    e.key === "Enter" &&
                                                                    handleNewAttribute({
                                                                        name: keySearch,
                                                                        setShowModal
                                                                    })
                                                                }
                                                                className="mb-4"
                                                                messageClass="text-red-500"
                                                                message={
                                                                    keySearch.length >= 25
                                                                        ? "Tối đa 25 ký tự"
                                                                        : ""
                                                                }
                                                                rightIcon={
                                                                    <span className="text-sm">
                                                                        {keySearch.length}/25
                                                                    </span>
                                                                }
                                                            />
                                                        </div>
                                                    </Tippy>
                                                </div>
                                            </Modal.Body>
                                            <Modal.Footer>
                                                <Button
                                                    className="rounded-md"
                                                    danger
                                                    outline
                                                    onClick={() => setShowModal(false)}
                                                >
                                                    Huỷ
                                                </Button>
                                            </Modal.Footer>
                                        </Modal>

                                        <Button
                                            onClick={() => setShowModal(true)}
                                            outline
                                            className="w-100 rounded-sm flex justify-center align-items-center"
                                        >
                                            <span className="mr-3">Thêm thuộc tính</span>
                                            <span>
                                                <BsPlusCircleDotted />
                                            </span>
                                        </Button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </Wrapper>
    );
}

export default DetailInformation;
