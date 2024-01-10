import { RiUserLine } from "react-icons/ri";
import { MdOutlineLocationOn } from "react-icons/md";
import { TbPhoneCall } from "react-icons/tb";

function AddressItem({ address, onClick }) {
    const { id, userId, receiverName, receiverPhone, note, wardId, street, detailsAddress } = address;

    return (
        <div className="rounded border border-blue-400 py-2 px-4">
            <div className="flex gap-1 items-center mb-2">
                <RiUserLine className="font-lg text-xl" />
                <span className="text-lg font-semibold">{receiverName}</span>
            </div>
            <div className="flex gap-1 items-center mb-2">
                <TbPhoneCall className="font-lg text-xl" />
                <span className="text-md font-semibold">{receiverPhone}</span>
            </div>
            <div className="flex gap-1 items-center mb-2">
                <MdOutlineLocationOn className="font-lg text-xl" />
                <span className="text-md text-wrap">{detailsAddress}</span>
            </div>
        </div>
    );
}

export default AddressItem;
