import React from 'react'
import './Product.css'
import demo from '../../../Images/no_cart.png'
import chinhHang from '../../../Images/chinhHang.png'

const Product = () => {
    return (
        <div className="col-2" style={{ padding: '0px 5px' }}>
            <div className="product-item-care">
                <div className="product-item-img">
                    <img src={demo} alt="" />
                </div>
                <div style={{ width: '50%', marginLeft: '10px' }}>
                    <img src={chinhHang} alt="" />
                </div>
                <h4 className="product-item-title">
                    Tee basic ss1 CREWZ áo thun tay lỡ unisex Local Brand - AO_THUN_DVR (V427)
                </h4>
                <div style={{ fontSize: '10px', marginLeft: '10px' }}>
                    <i className="fa-sharp fa-solid fa-star" style={{ color: '#fbc473' }}></i>
                    <i className="fa-sharp fa-solid fa-star" style={{ color: '#fbc473' }}></i>
                    <i className="fa-sharp fa-solid fa-star" style={{ color: '#fbc473' }}></i>
                    <i className="fa-sharp fa-solid fa-star" style={{ color: '#fbc473' }}></i>
                    <i className="fa-regular fa-star"></i>
                </div>
                <span
                    className="product-price"
                    style={{ fontSize: 15, color: "#515154", fontWeight: 500, marginLeft: '10px' }}>
                    49.000Đ
                </span>
            </div>
        </div>
    )
}

export default Product