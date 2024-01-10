import React from 'react'
import './TrendingItems.css'
import ProductTrending from '../ProductTrending/ProductTrending'

const TrendingItems = () => {
    return (
        <div className="row">
            <div className="row">
                <div className="col d-flex align-items-center justify-content-between">
                    <div className="d-flex mt-3">
                        <div>
                            <p style={{ fontSize: '20px', color: 'red', fontWeight: 'bold' }}>Tìm kiếm hàng đầu</p>
                        </div>
                        <div>

                        </div>
                    </div>
                    <div className="">
                        Xem tất cả
                    </div>
                </div>
            </div>
            <div className="row mb-2 ">
                <ProductTrending />
                <ProductTrending />
                <ProductTrending />
                <ProductTrending />
                <ProductTrending />
                <ProductTrending />
            </div>
        </div>
    )
}

export default TrendingItems