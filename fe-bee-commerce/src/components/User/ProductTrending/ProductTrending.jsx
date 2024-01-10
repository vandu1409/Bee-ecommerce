import React from 'react'
import './ProductTrending.css'
import demo from '../../../Images/no_cart.png'

const ProductTrending = () => {
    return (
        <div className="col-2" style={{ padding: '0px 5px' }}>
            <div className="productTrending-item">
                <div className="productTrending-item-img">
                    <img src={demo} alt="" />
                </div>
                <h4 className="productTrending-item-title">
                    Tee basic ss1 CREWZ áo thun tay lỡ unisex Local Brand - AO_THUN_DVR (V427)
                </h4>
                <div className="productTrending-item-price">
                    <span
                        className="product-price"
                        style={{ fontSize: 15, color: "black", fontWeight: 500 }}
                    >
                        Bán 49k / tháng
                    </span>
                </div>
                <div className="product-trending">
                    Top
                </div>

            </div>
        </div>
    )
}

export default ProductTrending