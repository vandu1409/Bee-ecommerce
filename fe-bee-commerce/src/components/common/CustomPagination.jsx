import React from 'react';
import { Pagination } from 'react-bootstrap';
import PropTypes from "prop-types";

function CustomPagination({ currentPage, totalPages, onChange = (page = 0) =>{} }) {
    const renderPaginationItems = () => {
        const items = [];

        // Render "First" and "Prev" buttons
        items.push(
            <Pagination.First key="first" onClick={() => onChange(0)}  disabled={currentPage === 0} />,
            <Pagination.Prev key="prev" onClick={() => onChange(currentPage - 1)} disabled={currentPage === 0} />
        );

        // Render page numbers and ellipsis
        for (let i = 0; i < totalPages; i++) {
            if (i === 0 || i === totalPages - 1 || (i >= currentPage - 1 && i <= currentPage + 1)) {
                items.push(
                    <Pagination.Item key={i} active={i === currentPage} onClick={() => onChange(i)}>
                        {i + 1}
                    </Pagination.Item>
                );
            } else if (i === currentPage - 2 || i === currentPage + 2) {
                items.push(<Pagination.Ellipsis key={`ellipsis-${i}`} />);
            }
        }

        // Render "Next" and "Last" buttons
        items.push(
            <Pagination.Next key="next" onClick={() => onChange(currentPage + 1)} disabled={currentPage === totalPages - 1} />,
            <Pagination.Last key="last" onClick={() => onChange(totalPages - 1)}  disabled={currentPage === totalPages - 1}/>
        );

        return items;
    };

    return <Pagination>{renderPaginationItems()}</Pagination>;
}

export default CustomPagination;


CustomPagination.propTypes = {
    currentPage: PropTypes.number.isRequired,
    totalPages: PropTypes.number.isRequired,
    onChange: PropTypes.func,
}
