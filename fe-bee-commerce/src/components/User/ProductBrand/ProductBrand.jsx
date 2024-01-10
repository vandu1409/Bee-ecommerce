import React from 'react'
import Product from '../Product/Product'
import './ProductBrand.css'

const ProductBrand = () => {
    return (
        <div className="row">
            <div className="row">
                <p style={{ fontSize: '16px', marginTop: '5px', fontWeight: 'bold', marginBottom: '0px' }}>Thương hiệu nổi bật</p>
            </div>
            <div className="row" style={{ marginTop: '10px' }}>
                <div className='ThuongHieu'>
                    <div className='ThuongHieu-item'>
                        <span>Cocacola</span>
                    </div>
                    <div className='ThuongHieu-item'>
                        <span>Cocacola</span>
                    </div>
                    <div className='ThuongHieu-item'>
                        <span>Cocacola</span>
                    </div>
                    <div className='ThuongHieu-item'>
                        <span>Cocacola</span>
                    </div>
                </div>
            </div>
            <div className="row mb-2 ">

                <Product />
                <Product />

                <Product />

                <Product />
                <Product />
                <Product />

            </div>
        </div>
    )
}

export default ProductBrand