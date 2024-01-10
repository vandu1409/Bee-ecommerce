import {useState} from "react";

function usePagination() {

    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const [size, setSize] = useState(10);

    const onChange = (page = 0) => {
        setCurrentPage(page);
    }

    const handlePagination = (data) => {

        data = data?.pagination || data;

        setCurrentPage(data.currentPage);
        setTotalPages(data.totalPages);
        setTotalElements(data.totalElements);
        setSize(data.size);
    }

    return {
        currentPage,
        totalPages,
        totalElements,
        size,
        onChange,
        handlePagination
    }
}

export default usePagination;