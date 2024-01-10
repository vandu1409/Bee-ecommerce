import { toCurrency } from "~/utils/CurrencyFormatter";
import { useState } from "react";
import Form from "react-bootstrap/Form";
import { SlPencil } from "react-icons/sl";
import Badge from "~/components/common/Badge";
import { FiEdit3 } from "react-icons/fi";
import { Link } from "react-router-dom";
import UpdatePriceModal from "~/pages/Product/UpdatePriceModal";

function CellWrapper({ children, className, isHover = false, ...passProps }) {
    className += isHover ? " bg-dark-100" : "";

    return (
        <div className={`flex p-2 h-10 cursor-pointer ${className}`} {...passProps}>
            {children}
        </div>
    );
}

function RowProduct({ id, poster, name, classifies = [], onUpdate = () => {} }) {
    const [hover, setHover] = useState(-1);

    const handleHover = (index) => {
        return {
            onMouseEnter: () => setHover(index),
            onMouseLeave: () => setHover(-1),
            isHover: index === hover,
        };
    };

    const handleUpdate = () => {
        onUpdate(id);
    };

    return (
        <>
            <tr>
                <td>
                    <div className="flex">
                        <Form.Check />
                        <img className="border rounded-3 ml-3" width={56} height={56} src={poster} alt={name} />
                        <div>
                            <span>{name}</span>
                        </div>
                    </div>
                </td>
                <td className="px-0">
                    {classifies.map((classify, index) => (
                        <CellWrapper className="gap-2" {...handleHover(index)}>
                            <Badge>{classify.classifyGroupValue}</Badge>
                            <Badge>{classify.classifyValue}</Badge>
                        </CellWrapper>
                    ))}
                </td>
                <td className="px-0">
                    {classifies.map((classify, index) => (
                        <CellWrapper className="flex align-items-center group " {...handleHover(index)}>
                            <span className="mr-2">{toCurrency(classify.price)}</span>
                            <SlPencil
                                onClick={handleUpdate}
                                className="opacity-0 group-hover:!opacity-100 text-warning"
                            />
                        </CellWrapper>
                    ))}
                </td>
                <td className="px-0">
                    {classifies.map((classify, index) => (
                        <CellWrapper className="flex align-items-center group" {...handleHover(index)}>
                            <span className="mr-2">{classify.inventory}</span>
                            <SlPencil
                                onClick={handleUpdate}
                                className="opacity-0 group-hover:!opacity-100 text-warning"
                            />
                        </CellWrapper>
                    ))}
                </td>
                <td className="px-0">
                    {classifies.map(({ price, quantity, inventory }, index) => (
                        <CellWrapper {...handleHover(index)}>{toCurrency(price * (quantity - inventory))}</CellWrapper>
                    ))}
                </td>
                <td>
                    <div className="flex flex-col gap-2">
                        <Link to="">
                            <Badge type="danger" icon={<FiEdit3 />}>
                                Xoá
                            </Badge>
                        </Link>
                    </div>
                </td>
            </tr>
        </>
    );
}

export default RowProduct;
