import { Button, Container, InputGroup, Row } from "react-bootstrap";
import React, { useEffect, useState } from "react";
import Form from "react-bootstrap/Form";
import Wrapper from "~/components/common/Wrapper";
import { useQuill } from "react-quilljs";
import "quill/dist/quill.snow.css";
import { LuSave } from "react-icons/lu";
import data from "bootstrap/js/src/dom/data";
import { useDispatch } from "react-redux";
import { hideLoader, showLoader } from "~/action/Loader.action";
import staticPagePreview from "~/pages/StaticPage/StaticPagePreview";
import staticPageApi from "~/api/staticPageApi";
import { NotificationManager } from "react-notifications";
import { useSearchParams } from "react-router-dom";

function StaticPageEdit({ children }) {
    const { quill, quillRef } = useQuill();
    const [title, setTitle] = useState("");
    const [path, setPath] = useState("");
    const [searchParams, setSearchParam] = useSearchParams();
    const [idUpdate, setIdUpdate] = useState(0);
    const dispatch = useDispatch();
    const { createPage, getStaticPage, updatePage } = staticPageApi();

    function handleGetData() {
        return quill?.root?.innerHTML;
    }

    function handlePreview() {
        const data = handleGetData();
        if (!data) return;
        const encodedData = encodeURIComponent(data);
        const encodedTitle = encodeURIComponent(title);
        const url = `/admin/static-page/preview?t=${encodedTitle}&d=${encodedData}`;
        // open in new tab
        window.open(url, "_blank");
    }

    function handleSave() {
        const data = handleGetData();
        if (!data) return;

        const StaticData = {
            content: data,
            title: title,
            path: path,
        };

        dispatch(showLoader());
        // call api
        new Promise((resolve, reject) => {
            if (idUpdate) {
                resolve(updatePage(idUpdate, StaticData));
            } else {
                resolve(createPage(StaticData));
            }
        })
            .then((res) => {
                NotificationManager.success(res.message, "Thành công");
            })
            .catch(({ data }) => {
                NotificationManager.error(data.message, "Lỗi");
            })
            .finally(() => {
                dispatch(hideLoader());
            });
    }

    function handlePathChange(e) {
        const value = e.target.value;
        const regex = /[^a-zA-Z0-9-_]/g;
        const result = value.replace(regex, "");
        setPath(result);
    }

    useEffect(() => {
        const editId = searchParams.get("edit");
        if (!editId) return;
        dispatch(showLoader());
        getStaticPage(editId)
            .then(({ data }) => {
                setTitle(data.title);
                setPath(data.path);
                setIdUpdate(Number(editId));
                // noinspection JSUnresolvedReference
                quill?.clipboard?.dangerouslyPasteHTML(data.content);
            })
            .catch((e) => {
                console.log(e);
                NotificationManager.error(e.data?.message, "Lỗi");
            })
            .finally(() => {
                dispatch(hideLoader());
            });
    }, [quill]);

    return (
        <Wrapper className="p-3">
            <Container>
                <h1 className="font-bold text-lg mb-3">Chỉnh sửa trang tĩnh</h1>
                <InputGroup size="sm" className="mb-3">
                    <InputGroup.Text id="inputGroup-sizing-sm">Đường dẫn trang</InputGroup.Text>
                    <Form.Control
                        value={path}
                        onChange={handlePathChange}
                        aria-label="Small"
                        aria-describedby="inputGroup-sizing-sm"
                    ></Form.Control>
                    <Button onClick={handlePreview}>Xem trước</Button>
                </InputGroup>
                <InputGroup size="sm" className="mb-3">
                    <InputGroup.Text id="inputGroup-sizing-sm">Tiêu đề trang</InputGroup.Text>
                    <Form.Control
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        aria-label="Small"
                        aria-describedby="inputGroup-sizing-sm"
                    ></Form.Control>
                </InputGroup>
                {/*QuillJS*/}
                <div style={{ height: 300 }} className="w-100 h-100 mb-3">
                    <div ref={quillRef}></div>
                </div>
                {/*QuillJS*/}

                <div className="flex justify-end">
                    <Button
                        onClick={handleSave}
                        variant={!idUpdate ? "success" : "warning"}
                        className="w-100 max-w-[300px] !flex gap-2 items-center justify-center"
                    >
                        <span>{idUpdate ? "Cập nhật" : "Lưu"}</span>
                        <LuSave />
                    </Button>
                </div>
            </Container>
        </Wrapper>
    );
}

export default StaticPageEdit;
