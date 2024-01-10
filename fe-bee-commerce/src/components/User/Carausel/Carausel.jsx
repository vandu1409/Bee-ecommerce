import React from 'react'
import Carousel from 'react-bootstrap/Carousel';
import './Carausel.css'

const CarouselComponent = () => {
    return (
        <div className="mb-6 w-100">

            <Carousel className='px-0'>
                <Carousel.Item>
                    <img
                        className="d-block img-fluid carousel-image"
                        src="https://lzd-img-global.slatic.net/g/icms/images/ims-web/1f93ae24-164e-4fa7-b2af-c4a320e1d03c.jpg_2200x2200q90.jpg_.webp"
                        alt="First slide"
                        width={'100%'} height={'100%'}

                    />
                </Carousel.Item>
                <Carousel.Item>
                    <img
                        className="d-block img-fluid carousel-image"
                        src="https://lzd-img-global.slatic.net/g/icms/images/ims-web/24c34b3c-7c8f-41a3-a094-d6eaf140b32c.jpg_2200x2200q90.jpg_.webp"
                        alt="Second slide"
                        width={'100%'} height={'100%'}
                    />
                </Carousel.Item>
                <Carousel.Item>
                    <img
                        className="d-block img-fluid carousel-image"
                        src="https://lzd-img-global.slatic.net/g/icms/images/ims-web/c93276c3-33ae-4fba-a0ff-8f2ef0cb1e27.jpg_2200x2200q90.jpg_.webp"
                        alt="third slide"
                        width={'100%'} height={'100%'}
                    />
                </Carousel.Item>
                <Carousel.Item>
                    <img
                        className="d-block img-fluid carousel-image"
                        src="https://lzd-img-global.slatic.net/g/icms/images/ims-web/f322b9ee-5185-45e2-a5b6-292ec3c4659d.jpg_2200x2200q90.jpg_.webp"
                        alt="four slide"
                        width={'100%'} height={'100%'}
                    />
                </Carousel.Item>
            </Carousel>

            {/* <div className="col-4" style={{ paddingLeft: '5px' }}>
                <div style={{ marginBottom: '8px' }}>
                    <img src="https://cf.shopee.vn/file/vn-50009109-88c7abdef2d39cf147dfbbf4c3a20d1b_xhdpi" alt="" className="img-fluid"
                        style={{ borderRadius: '15px' }} />
                </div>
                <div style={{}}>
                    <img src="https://cf.shopee.vn/file/vn-50009109-88c7abdef2d39cf147dfbbf4c3a20d1b_xhdpi" alt="" className="img-fluid" style={{ borderRadius: '15px' }} />
                </div>
            </div> */}
        </div>
    )
}

export default CarouselComponent