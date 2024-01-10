import React from 'react'
import './ProductCare.css'
import Product from '../Product/Product'

const ProductCare = () => {
    return (
        <div className="row">
            <div className="row">
                <div className="col">
                    <p style={{ fontSize: '16px', marginTop: '5px', fontWeight: 'bold', marginBottom: '0px' }}>Sản phẩm quan tâm</p>
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

export default ProductCare