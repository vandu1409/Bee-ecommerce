import React from 'react'
import Product from '../Product/Product'

const SuggestItem = () => {
    return (
        <div className='col'>
            <div className="row">
                <div style={{ borderBottom: '5px solid red', width: '100%', backgroundColor: 'white', marginTop: '20px', marginBottom: '10px' }}>
                    <p style={{ textAlign: 'center', margin: 'auto', padding: '10px 0px' }}>Gợi ý hôm nay</p>
                </div>
            </div>
            <div className="row">
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />
                <Product />
            </div>
            <div className="row pb-3 mt-3">
                <div className="col text-center">
                    <button className="btn btn-outline-secondary" style={{ width: '400px', padding: '7px 0px 7px 0px' }}>
                        Xem thêm
                    </button>

                </div>
            </div >
        </div>
    )
}

export default SuggestItem