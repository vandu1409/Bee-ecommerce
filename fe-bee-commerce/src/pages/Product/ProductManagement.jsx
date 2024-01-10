import Wrapper from "~/components/common/Wrapper";
import { Button, FormControl, FormGroup, InputGroup, Nav, Table } from "react-bootstrap";
import { Link } from "react-router-dom";
import RowProduct from "~/pages/Product/RowProduct";
import { useEffect, useState } from "react";
import CustomPagination from "~/components/common/CustomPagination";
import UpdatePriceModal from "~/pages/Product/UpdatePriceModal";
import productsApi from "~/api/productsApi";
import usePagination from "~/hooks/usePagination";
import {useDispatch} from "react-redux";
import {hideLoader, showLoader} from "~/action/Loader.action";

const data = [
    {
        id: 0,
        name: "Điện thoại Iphone 12 Pro Max",
        poster: "https://storagefpl.s3.ap-southeast-2.amazonaws.com/bee_ecommorce1668ba52-f9a3-498d-98d1-dd77864c427b1701495041862-cap-type-c-lightning-mfi-1-5m-innostyle-powerflex-icl150alblk-thumb-600x600.jpg",
        sold: 0,
        likedCount: 0,
        discount: {},
        voteCount: 0,
        star: {},
        inStock: {},
        classifies: [
            {
                classifyGroupValue: "Đỏ",
                classifyValue: "Demo",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Xanh",
                classifyValue: "128GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Xanh",
                classifyValue: "256GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Xanh",
                classifyValue: "512GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Xanh",
                classifyValue: "1TB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Đen",
                classifyValue: "128GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Đen",
                classifyValue: "256GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Đen",
                classifyValue: "512GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Đen",
                classifyValue: "1TB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
        ],
    },
    {
        id: 1,
        name: "Điện thoại Iphone 12 Pro Max",
        poster: "https://storagefpl.s3.ap-southeast-2.amazonaws.com/bee_ecommorce1668ba52-f9a3-498d-98d1-dd77864c427b1701495041862-cap-type-c-lightning-mfi-1-5m-innostyle-powerflex-icl150alblk-thumb-600x600.jpg",
        sold: 0,
        likedCount: 0,
        discount: {},
        voteCount: 0,
        star: {},
        inStock: {},
        classifies: [
            {
                classifyGroupValue: "Đỏ",
                classifyValue: "31231",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Đỏ",
                classifyValue: "256GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Đỏ",
                classifyValue: "512GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Đỏ",
                classifyValue: "1TB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Xanh",
                classifyValue: "128GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Xanh",
                classifyValue: "256GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Xanh",
                classifyValue: "512GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Xanh",
                classifyValue: "1TB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Đen",
                classifyValue: "128GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Đen",
                classifyValue: "256GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Đen",
                classifyValue: "512GB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
            {
                classifyGroupValue: "Đen",
                classifyValue: "1TB",
                price: 4900000,
                inventory: 123,
                sold: 12,
            },
        ],
    },
];

function ProductManagement({ children }) {
    const [products, setProducts] = useState([]);
    const [classifies, setClassifies] = useState([]);
    const dispatch = useDispatch();
    const { getAllProductsDetail } = productsApi();
    const { currentPage, totalPages, onChange: handlePageChange, handlePagination } = usePagination();

    const handleUpdate = (id) => {
        setClassifies(products.find((item) => item.id === id));
    };

    useEffect(() => {
        dispatch(showLoader());
        getAllProductsDetail(currentPage).then((r) => {
            handlePagination(r.pagination);
            setProducts(r.data);
        }).finally(() => dispatch(hideLoader()));
    }, [currentPage]);

    return (
        <div className="container">
            <Wrapper className="p-3 mb-4">
                <div className="row px-3">
                    <div className="col-md-6 mb-3">
                        <InputGroup>
                            <InputGroup.Text>Tên sản phẩm</InputGroup.Text>
                            <FormControl placeholder="Vui lòng nhập ít nhất 3 ký tự để tìm" />
                        </InputGroup>
                    </div>
                    <div className="col-md-6  mb-3">
                        <InputGroup>
                            <InputGroup.Text>Ngành hàng</InputGroup.Text>
                            <FormControl placeholder="Click để chọn ngành hàng" className="cursor-pointer" readOnly />
                        </InputGroup>
                    </div>
                    <div className="col-12 col-md-3">
                        <Button className="mr-4">Tìm kiếm</Button>
                        <Button className="mr-4">Nhập lại</Button>
                    </div>
                </div>
            </Wrapper>
            <Wrapper className="p-3">
                <div className="row">
                    <div className="col-12 mb-3">
                        <Nav variant="underline" defaultActiveKey="/home">
                            <Nav.Item>
                                <Link to={"#Demo"} className="nav-link text-lg font-medium">
                                    Tất cả sản phẩm
                                </Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Link to={"#Demo"} className="nav-link text-lg font-medium">
                                    Đang hoạt động
                                </Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Link to={"#Demo"} className="nav-link text-lg font-medium">
                                    Chờ duyệt
                                </Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Link to={"#Demo"} className="nav-link text-lg font-medium">
                                    Đã ẩn
                                </Link>
                            </Nav.Item>
                        </Nav>
                    </div>
                    <div className="col-12">
                        <Table className="border table-bordered " responsive>
                            <thead>
                                <tr>
                                    <th className="font-semibold">Tên sản phẩm</th>
                                    <th className="font-semibold">Phân loại hàng</th>
                                    <th className="font-semibold">Giá</th>
                                    <th className="font-semibold">Kho hàng</th>
                                    <th className="font-semibold">Doanh số</th>
                                    <th className="font-semibold">Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                {products.map((item, index) => (
                                    <RowProduct onUpdate={handleUpdate} key={item.id} {...item} />
                                ))}
                            </tbody>
                        </Table>
                    </div>
                </div>

                <div>
                    <CustomPagination currentPage={currentPage} totalPages={totalPages} onChange={handlePageChange} />
                </div>
            </Wrapper>
            <UpdatePriceModal onHide={() => setClassifies([])} show={classifies.length !== 0} product={classifies} />
        </div>
    );
}

export default ProductManagement;
