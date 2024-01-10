import {useContext} from "react";
import Input from "~/components/common/Input";
import {CreateProductContext} from "./CreateProduct";
import {reverseCurrency, toCurrency} from "~/utils/CurrencyFormatter";
import {useForm} from "react-hook-form";

function TableSalePrice({ data }) {
    const [classification, setClassification] = data;

    const { saleInfo, setSaleInfo, setValidator } = useContext(CreateProductContext);

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm({
        mode: "onBlur",
        reValidateMode: "onChange",
    });

    // Hàm này xử lý nhập cho bảng giá và kho hàng
    const handleAddSaleInfo = (classifyGroup, classify, price, inventory) => {
        price = price && reverseCurrency(price);

        const newSaleInfo = [...saleInfo];

        const existingItemIndex = newSaleInfo.findIndex(
            (item) => item.classifyGroupValue === classifyGroup && item.classifyValue === classify,
        );

        if (existingItemIndex !== -1) {
            const existingItem = newSaleInfo[existingItemIndex];
            existingItem.price = price == null ? existingItem.price : price;
            const strInventory = inventory == null ? existingItem.inventory : inventory;
            existingItem.inventory = strInventory === "" ? 0 : parseInt(strInventory);
        } else {
            newSaleInfo.push({
                classifyGroupValue: classifyGroup,
                classifyValue: classify,
                price: price || 0,
                inventory: inventory || 0,
            });
        }

        setSaleInfo(newSaleInfo);
    };

    // Hàm này lấy ra giá và kho hàng của một phân loại từ bảng giá và kho hàng
    const getSaleInfo = (classifyGroup, classify) => {
        const result = { price: 0, inventory: 0 };
        saleInfo.some(({ classifyGroupValue, classifyValue, price, inventory }) => {
            if (classifyGroupValue === classifyGroup && classifyValue === classify) {
                result.price = price;
                result.inventory = inventory;
                return true;
            }
            return false;
        });
        return result;
    };

    const renderSingleClassifyRow = (classifyGroupValue, index) => (
        <tr key={index} className="bg-white border-b border-x">
            <td className="px-6 py-4">{classifyGroupValue}</td>
            <td className="px-6 py-4">
                <Input
                    inputRef={{
                        ...register(`price${index}`, {
                            min: { message: "Giá phải lớn hơn 0", value: 0 },
                            // setValueAs: (value) => reverseCurrency(value),
                            required: { message: "Giá không được để trống", value: true },
                            onChange: (e) => handleAddSaleInfo(classifyGroupValue, null, e.target.value, null),
                        }),
                    }}
                    value={toCurrency(getSaleInfo(classifyGroupValue, null).price, false)}
                    // onChange={(e) => handleAddSaleInfo(classifyGroupValue, null, e.target.value, null)}
                    rightIcon={<span className="text-sm block h-100">đ</span>}
                />
            </td>
            <td className="px-6 py-4">
                <Input
                    value={getSaleInfo(classifyGroupValue, null).inventory}
                    onChange={(e) => handleAddSaleInfo(classifyGroupValue, null, null, e.target.value)}
                    leftIcon={<span className="text-sm block h-100"></span>}
                />
            </td>
        </tr>
    );

    const renderDoubleClassifyRow = (classifyGroupValue, classifyValue, indexGroup, indexClassify) => (
        <tr key={indexGroup * 99 + indexClassify} className="bg-white border-b border-x">
            {indexClassify === 0 && (
                <th
                    scope="row"
                    rowSpan={classification[1].value.length}
                    className="px-6 py-4 font-medium text-dark-900 whitespace-nowrap"
                >
                    {classifyGroupValue}
                </th>
            )}
            <td className="px-6 py-4">{classifyValue}</td>
            <td className="px-6 py-4">
                <Input
                    value={toCurrency(getSaleInfo(classifyGroupValue, classifyValue).price, false)}
                    onChange={(e) => handleAddSaleInfo(classifyGroupValue, classifyValue, e.target.value, null)}
                    rightIcon={<span className="text-sm block h-100">đ</span>}
                />
            </td>
            <td className="px-6 py-4">
                <Input
                    value={getSaleInfo(classifyGroupValue, classifyValue).inventory}
                    onChange={(e) => handleAddSaleInfo(classifyGroupValue, classifyValue, null, e.target.value)}
                    leftIcon={<span className="text-sm block h-100"></span>}
                />
            </td>
        </tr>
    );

    return (
        <div className="flex mb-4">
            <span className="w-[180px] text-right pr-8">Danh sách phân loại hàng</span>
            <div className="flex-1">
                <div className="relative overflow-x-auto">
                    <div className="relative overflow-x-auto shadow-md sm:rounded-lg">
                        <table className="w-full text-sm text-left text-dark-800 dark:text-dark-400">
                            <thead className="text-xs text-dark-800 uppercase bg-dark-50">
                                <tr>
                                    {classification[0] && (
                                        <th scope="col" className="px-6 py-3">
                                            {classification[0].name || "Nhóm phân loại 1"}
                                        </th>
                                    )}
                                    {classification[1] && (
                                        <th scope="col" className="px-6 py-3">
                                            {classification[1].name || "Nhóm phân loại 2"}
                                        </th>
                                    )}
                                    <th scope="col" className="px-6 py-3">
                                        Giá
                                    </th>
                                    <th scope="col" className="px-6 py-3">
                                        Kho hàng
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {classification[0] &&
                                    classification[0].value.map((classifyGroupValue, index) => {
                                        return classification[1]
                                            ? classification[1].value.map((classifyValue, index2) =>
                                                  renderDoubleClassifyRow(
                                                      classifyGroupValue,
                                                      classifyValue,
                                                      index,
                                                      index2,
                                                  ),
                                              )
                                            : renderSingleClassifyRow(classifyGroupValue, index);
                                    })}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default TableSalePrice;
