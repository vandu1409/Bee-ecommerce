import Wrapper from "~/components/common/Wrapper";
import {Table} from "react-bootstrap";
import Badge from "~/components/common/Badge";
import staticPageApi from "~/api/staticPageApi";
import {useEffect, useState} from "react";
import {useDispatch} from "react-redux";
import {hideLoader, showLoader} from "~/action/Loader.action";
import CustomPagination from "~/components/common/CustomPagination";
import {NotificationManager} from "react-notifications";
import {Link} from "react-router-dom";
import {FaPlus} from "react-icons/fa6";
import {RxExternalLink} from "react-icons/rx";

function StaticPageManager({children}) {
    const {getStaticPages, deletePage} = staticPageApi();
    const [pagination, setPagination] = useState({totalPages: 0, currentPage: 0});
    const [staticPages, setStaticPages] = useState([]);
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(showLoader());
        getStaticPages()
            .then(({data, pagination}) => {
                setStaticPages(data);
                setPagination({
                    totalPages: pagination.totalPages,
                    currentPage: pagination.currentPage,
                });
            })
            .catch(({data}) => {
                NotificationManager.error(data.message, "Lỗi");
            })
            .finally(() => {
                dispatch(hideLoader());
            });
    }, []);

    function handleDelete(id) {
        dispatch(showLoader());
        deletePage(id)
            .then(() => {
                NotificationManager.success("Xóa thành công", "Thành công");
                setStaticPages(staticPages.filter((page) => page.id !== id));
            })
            .catch(({data}) => {
                NotificationManager.error(data.message, "Lỗi");
            })
            .finally(() => {
                dispatch(hideLoader());
            });
    }

    return (
        <>
            <Wrapper className="p-5">
                <div className="flex justify-between mb-3">
                    <span className=" text-2xl font-bold">Danh sách các trang tĩnh</span>
                    <Link to="/admin/static-page/new" className="btn btn-primary !flex gap-2 items-center"><span>Tạo mới</span><FaPlus/></Link>
                </div>
                <Table className="rounded-2xl" variant="table" striped bordered hover size="sm">
                    <thead>
                    <tr>
                        <th>Tiêu đề</th>
                        <th>Đường dẫn</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    {staticPages.map(({title, path, id}, index) => (
                        <tr key={index}>
                            <td className="py-1 px-2">{title}</td>
                            <td className="py-1 px-2">
                                <a className="flex items-center gap-2 text-blue-900 hover:!text-blue-600" target="_blank" href={`/static-page?p=${path}`}>{path}<RxExternalLink /></a>
                            </td>
                            <td className="py-1 px-2 space-x-1">
                                <Link to={`/admin/static-page/new?edit=${id}`}>
                                    <Badge type="warning">Edit</Badge>
                                </Link>
                                <span className="cursor-pointer" onClick={() => handleDelete(id)}>
                                        <Badge type="danger">Delete</Badge>
                                    </span>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
                <CustomPagination {...pagination} />
            </Wrapper>
        </>
    );
}

export default StaticPageManager;
