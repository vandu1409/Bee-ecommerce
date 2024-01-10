import {Fragment, useContext, useEffect, useState} from "react";
import {IoClose} from "react-icons/io5";
import Button from "~/components/common/Button";
import Input from "~/components/common/Input";
import Wrapper from "~/components/common/Wrapper";
import TableSalePrice from "./TableSalePrice";
import {CreateProductContext} from "~/pages/CreateProduct/CreateProduct";
import {reverseCurrency, toCurrency} from "~/utils/CurrencyFormatter";

function SalesInformation() {
    const [classification, setClassification] = useState([]);

    const { productData, setProductData, saleInfo, setSaleInfo } = useContext(CreateProductContext);

    useEffect(() => {
        // Update lại dữ liệu cho bảng giá vào kho hàng vào thông tin sản phẩm
        setProductData({
            ...productData,
            classifyGroupName: {
                name: classification[0]?.name,
            },
            classifyName: {
                name: classification[1]?.name,
            },
        });

        // Cập nhật dữ liệu cho saleInfo khi classification thay đổi
        const newSaleInfo = saleInfo.filter(({ classifyGroupValue, classifyValue }) => {
            return (
                classification[1]?.value?.some((value) => value === classifyValue) &&
                classification[0]?.value?.some((value) => value === classifyGroupValue)
            );
        });

        setSaleInfo(newSaleInfo);
    }, [classification]);

    const handleAddClassification = () => {
        setClassification([...classification, { name: "", value: [] }]);
    };

    const handleRemoveClassification = (index) => {
        const newClassification = [...classification];
        newClassification.splice(index, 1);
        setClassification(newClassification);
    };

    const handleRemoveValue = (index, valueIndex) => {
        const newClassification = [...classification];
        newClassification[index].value.splice(valueIndex, 1);
        setClassification(newClassification);
    };

    // Đổi tên nhóm phân loại
    const handleRenameClassification = (index, name) => {
        const newClassification = [...classification];
        newClassification[index].name = name;

        // const oldName = classification[index].name;
        //
        // const newSaleIfo = saleInfo.map((item) => {
        //     if (item.classifyGroupValue === oldName) {
        //         item.classifyGroupValue = name;
        //     }
        //
        //     if (item.classifyValue === oldName) {
        //         item.classifyValue = name;
        //     }
        //
        //     return item;
        // });
        //
        // setSaleInfo(newSaleIfo);
        setClassification(newClassification);
    };

    const handleRenameValue = (index, valueIndex, name) => {
        const newClassification = [...classification];
        newClassification[index].value[valueIndex] = name;
        setClassification(newClassification);
    };

    // Hàm này sử dụng khi không có phân loại (để phân loại là undefined)
    const handleAddSaleInfo = (price, inventory) => {

        price = price && reverseCurrency(price);

        const newSaleInfo = saleInfo.filter(
            (item) => item.classifyGroupValue === undefined && item.classifyValue === undefined,
        );

        setSaleInfo([
            {
                price: price || newSaleInfo[0]?.price || 0,
                inventory: inventory || newSaleInfo[0]?.inventory || 0,
            },
        ]);
    };

    return (
        <Wrapper className="p-4 pb-0 mb-9">
            <h1 className="text-2xl font-semibold mb-3">Thông tin bán hàng</h1>
            <div className="flex mb-4">
                <div className="flex-1">
                    <div className="flex mb-4">
                        <span className="w-[180px] text-right pr-8">Phân loại hàng</span>
                        <div className="flex-1 mb-3">
                            {classification.map(({ name, value }, index) => (
                                <Fragment key={index}>
                                    {/* Phần chứa tên của nhóm phân loại */}
                                    <div className="flex align-items-center pb-3 bg-dark-300 bg-opacity-20 py-2 px-4">
                                        <div className="w-[100px] mr-4 ">Nhóm phân loại {index + 1}</div>
                                        <div className="flex-1">
                                            <Input
                                                value={name}
                                                onChange={(e) => handleRenameClassification(index, e.target.value)}
                                                className="bg-tw-white w-100"
                                                placeholder="vd: Màu sắc v.v"
                                            />
                                        </div>
                                        <span className="flex-1 text-xl flex justify-end align-items-center">
                                            <IoClose onClick={() => handleRemoveClassification(index)} />
                                        </span>
                                    </div>
                                    {/* Phần chứa các input điền value cho nhóm phân loại  */}
                                    <div className="flex align-items-start bg-dark-300 bg-opacity-20 py-2 px-4 mb-3">
                                        <div className="w-[100px] mr-4 ">Phân loại hàng</div>
                                        <div className="grid grid-cols-2 gap-3 flex-1">
                                            {[...value, ""].map((item, valueIndex) => (
                                                <div key={valueIndex} className="">
                                                    <Input
                                                        value={item}
                                                        onChange={(e) =>
                                                            handleRenameValue(index, valueIndex, e.target.value)
                                                        }
                                                        rightIcon={
                                                            <IoClose
                                                                onClick={() => handleRemoveValue(index, valueIndex)}
                                                            />
                                                        }
                                                        className="bg-tw-white w-100"
                                                        placeholder="vd: Trắng, Đen, S, M v.v"
                                                    />
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                </Fragment>
                            ))}
                            {classification.length < 2 && (
                                <>
                                    <Button onClick={handleAddClassification}>Thêm phân loại</Button>
                                </>
                            )}
                        </div>
                    </div>

                    {classification.length > 0 && <TableSalePrice data={[classification, setClassification]} />}

                    {/*Nhập giá khi không có phân loại*/}
                    {classification.length === 0 && (
                        <>
                            <div className="flex mb-4">
                                <span className="w-[180px] text-right pr-8">Giá bán</span>
                                <div className="flex-1">
                                    <Input
                                        value={toCurrency(saleInfo[0]?.price, false)}
                                        onChange={(e) => handleAddSaleInfo(e.target.value, null)}
                                    />
                                </div>
                            </div>
                            <div className="flex mb-4">
                                <span className="w-[180px] text-right pr-8">Kho hàng</span>
                                <div className="flex-1">
                                    <Input
                                        value={saleInfo[0]?.inventory || 0}
                                        onChange={(e) => handleAddSaleInfo(null, e.target.value)}
                                    />
                                </div>
                            </div>
                        </>
                    )}
                </div>
            </div>
        </Wrapper>
    );
}

export default SalesInformation;
