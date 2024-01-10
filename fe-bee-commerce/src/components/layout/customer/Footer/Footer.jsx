import React from "react";
import "./Footer.css";

const Footer = () => {
    return (
        <div className="container-fluid">
            <div className="row" style={{ backgroundColor: "white", padding: "15px" }}>
                <div className="col-1"></div>
                <div className="col-2">
                    <p className="fotter-heading">Hỗ trợ khách hàng</p>
                    <ul className="footer-list">
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Hotline: 0703414576
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Các câu hỏi thường gặp
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Gửi yêu cầu hỗ trợ
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Hướng dẫn đặt hàng
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Phương thức vận chuyển
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Chính sách đổi trả
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Hướng dẫn trả góp
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Chính sách hàng nhập khẩu
                            </a>
                        </li>
                    </ul>
                </div>

                <div className="col-2">
                    <p className="fotter-heading">Về Shop Bee</p>
                    <ul className="footer-list">
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Tuyển dụng
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Chính sách bảo mật thanh toán
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Chính sách bảo mật thông tin cá nhân
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Chính sách giải quyết khiếu nại
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Điều khoản sử dụng
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Bán hàng doanh nghiệp
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Điều kiện vận chuyển
                            </a>
                        </li>
                    </ul>
                </div>

                <div className="col-2">
                    <p className="fotter-heading">Hợp tác và liên kết</p>
                    <ul className="footer-list">
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Quy chế hoạt động Sàn GDTMĐT
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Bán hàng cùng Shop Bee
                            </a>
                        </li>
                        <li className="footer-list-item">
                            <a href="/" className="footer-item-link">
                                Hướng dẫn mua hàng
                            </a>
                        </li>
                    </ul>
                    <p className="fotter-heading mt-2">Chứng nhận bởi</p>
                    <div className="flex items-center  space-x-2">
                        <div>
                            <img src="https://frontend.tikicdn.com/_desktop-next/static/img/footer/bo-cong-thuong-2.png" width={50} alt="" />
                        </div>
                        <div>
                            <img src="https://frontend.tikicdn.com/_desktop-next/static/img/footer/bo-cong-thuong.svg" width={80} alt="" />
                        </div>
                    </div>
                </div>
                <div className="col-2">
                    <p className="fotter-heading">Phương thức thanh toán</p>
                    <div className="flex items-center space-x-2">
                        <span className="footer-icon">
                            <svg width="32" height="33" viewBox="0 0 32 33" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    fillRule="evenodd"
                                    clipRule="evenodd"
                                    d="M30 10.3615C30 8.8731 28.7934 7.6665 27.305 7.6665H4.695C3.20659 7.6665 2 8.8731 2 10.3615V22.9715C2 24.4599 3.20659 25.6665 4.695 25.6665H27.305C28.7934 25.6665 30 24.4599 30 22.9715V10.3615ZM4.695 8.6665H27.305L27.4513 8.67273C28.3189 8.74688 29 9.47465 29 10.3615V22.9715L28.9938 23.1178C28.9196 23.9854 28.1919 24.6665 27.305 24.6665H4.695L4.54875 24.6603C3.6811 24.5861 3 23.8584 3 22.9715V10.3615L3.00622 10.2153C3.08037 9.3476 3.80815 8.6665 4.695 8.6665Z"
                                    fill="#052E5C"
                                ></path>
                                <path
                                    fillRule="evenodd"
                                    clipRule="evenodd"
                                    d="M8.67528 20.2746L8.14557 21.881H7L9.205 15.6665H10.4582L12.6632 21.881H11.4918L10.9621 20.2746H8.67528ZM8.93368 19.4176H10.6994L9.83377 16.7647H9.80362L8.93368 19.4176ZM15.9535 21.881V16.6054H17.8097V15.6665H12.9862V16.6054H14.8467V21.881H15.9535ZM19.5711 17.471V21.881H18.5676V15.6665H19.8553L21.7589 20.4081H21.7933L23.6968 15.6665H24.9802V21.881H23.9811V17.471H23.9509L22.1551 21.881H21.3971L19.6012 17.471H19.5711Z"
                                    fill="#052E5C"
                                ></path>
                                <rect x="22" y="10.6665" width="5" height="3" rx="1" fill="#0B74E5"></rect>
                            </svg>
                        </span>
                        <span className="footer-icon">
                            <img src="https://vnpay.vn/assets/images/logo-icon/logo-primary.svg" width={60} alt="" />
                        </span>
                        <span className="footer-icon">
                            <svg width="32" height="33" viewBox="0 0 32 33" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    fillRule="evenodd"
                                    clipRule="evenodd"
                                    d="M2.17957 6.6665C3.05501 6.6665 3.76471 7.37619 3.76471 8.25164V8.54886H11.6637C12.8114 8.54886 13.3324 8.8915 14.2358 10.0787L15.8465 12.3136H30.2624C31.222 12.3136 32 13.1068 32 14.0852V25.6008C32 26.5792 31.222 27.3724 30.2624 27.3724H11.1493C10.1897 27.3724 9.41174 26.5792 9.41174 25.6008V20.3498H8.03056C7.20288 20.3498 6.41436 19.9729 5.68079 19.3563C5.51511 19.217 5.35997 19.0716 5.21619 18.924L4.96562 18.652L4.906 18.5825L3.73127 18.5832C3.58119 19.3026 2.94348 19.843 2.17957 19.843H1.58514C0.709691 19.843 0 19.1333 0 18.2578V8.25164C0 7.37619 0.709691 6.6665 1.58514 6.6665H2.17957ZM3.76471 17.6423H5.3697L5.542 17.8741L5.64335 17.9969C5.66442 18.0215 5.68754 18.048 5.71264 18.0762C5.88231 18.2671 6.07526 18.4583 6.28641 18.6358C6.80818 19.0744 7.34414 19.3496 7.85956 19.4002L8.03056 19.4087H9.41174V16.7807L8.78495 16.7809C7.74476 16.7809 7.00673 16.4227 6.5663 15.8019L6.47647 15.6649C6.23717 15.2693 6.14194 14.8715 6.12188 14.4795L6.11768 14.3119H7.05885C7.05885 14.6016 7.11528 14.9025 7.28177 15.1777C7.51253 15.5592 7.9174 15.7976 8.5937 15.8346L8.78495 15.8397H10.6145L12.0877 17.1837C12.6209 17.5716 13.4146 17.5821 14.0576 17.2702C14.5588 17.0271 14.6664 16.8014 14.5092 16.6584L11.2886 13.7286L11.2941 12.5251V12.3136H14.7371L13.5291 10.6377C12.7952 9.67451 12.5004 9.48067 11.6637 9.48067H3.76471V17.6423ZM2.17957 7.45907H1.58514C1.18109 7.45907 0.847652 7.76143 0.798745 8.15223L0.79257 8.25164V18.2578C0.79257 18.6619 1.09492 18.9953 1.48572 19.0442L1.58514 19.0504H2.17957C2.58362 19.0504 2.91705 18.7481 2.96596 18.3573L2.97214 18.2578V8.25164C2.97214 7.84759 2.66978 7.51416 2.27898 7.46525L2.17957 7.45907ZM10.3529 25.5528V16.8741L11.4942 17.9124L11.6621 18.0256C12.5171 18.5551 13.5921 18.5421 14.4684 18.117C15.4765 17.628 15.9294 16.6781 15.1425 15.9622L12.1664 13.2547H30.1961C30.6725 13.2547 31.0588 13.648 31.0588 14.1332V25.5528C31.0588 26.0379 30.6725 26.4312 30.1961 26.4312H11.2157C10.7392 26.4312 10.3529 26.0379 10.3529 25.5528Z"
                                    fill="#052E5C"
                                ></path>
                                <circle cx="20.7059" cy="19.843" r="4.70588" fill="#0B74E5"></circle>
                                <path
                                    d="M21.5934 19.6898L20.0494 19.3178C19.8206 19.2623 19.6604 19.0838 19.6604 18.8833C19.6604 18.634 19.9006 18.4315 20.1955 18.4315H21.1605C21.3759 18.4315 21.5809 18.488 21.7521 18.593C21.8303 18.6408 21.9409 18.6252 22.0094 18.5674L22.3079 18.3155C22.3994 18.2382 22.3848 18.1121 22.2821 18.0457C21.9615 17.8381 21.5715 17.7256 21.1605 17.7256H21.1244V17.1959C21.1244 17.0984 21.0308 17.0194 20.9153 17.0194H20.497C20.3814 17.0194 20.2878 17.0984 20.2878 17.1959V17.7254H20.1955C19.3958 17.7254 18.7542 18.3055 18.8298 18.9933C18.8836 19.4822 19.3281 19.8784 19.8873 20.0129L21.3629 20.3683C21.5916 20.4239 21.7519 20.6024 21.7519 20.8029C21.7519 21.0522 21.5116 21.2547 21.2167 21.2547H20.2517C20.0363 21.2547 19.8314 21.1982 19.6601 21.0932C19.5819 21.0453 19.4714 21.061 19.4029 21.1188L19.1043 21.3707C19.0128 21.4479 19.0274 21.5739 19.1302 21.6405C19.4507 21.8481 19.8408 21.9606 20.2517 21.9606H20.2878V22.49C20.2878 22.5875 20.3814 22.6665 20.497 22.6665H20.9153C21.0308 22.6665 21.1244 22.5875 21.1244 22.49V21.9606H21.16C21.7563 21.9606 22.3189 21.6608 22.5121 21.1848C22.7775 20.5309 22.3218 19.8658 21.5934 19.6898Z"
                                    fill="white"
                                ></path>
                            </svg>
                        </span>
                    </div>
                </div>
                <div className="col-2">
                    <p className="fotter-heading">Kết nối với chúng tôi</p>
                    <div className="flex items-center space-x-3">
                        <span>
                            <svg width="32" height="33" viewBox="0 0 32 33" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M0 16.6665C0 7.82995 7.16344 0.666504 16 0.666504C24.8366 0.666504 32 7.82995 32 16.6665C32 25.5031 24.8366 32.6665 16 32.6665C7.16344 32.6665 0 25.5031 0 16.6665Z"
                                    fill="#3B5998"
                                ></path>
                                <path
                                    d="M17.6676 26.0742V17.3693H20.0706L20.389 14.3696H17.6676L17.6717 12.8682C17.6717 12.0858 17.7461 11.6666 18.8698 11.6666H20.372V8.6665H17.9687C15.082 8.6665 14.066 10.1217 14.066 12.5689V14.3699H12.2666V17.3696H14.066V26.0742H17.6676Z"
                                    fill="white"
                                ></path>
                            </svg>
                        </span>
                        <span>
                            <svg width="32" height="33" viewBox="0 0 32 33" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M0 16.6665C0 7.82995 7.16344 0.666504 16 0.666504C24.8366 0.666504 32 7.82995 32 16.6665C32 25.5031 24.8366 32.6665 16 32.6665C7.16344 32.6665 0 25.5031 0 16.6665Z"
                                    fill="#FF0000"
                                ></path>
                                <path
                                    d="M24.1768 12.7153C23.9805 11.9613 23.4022 11.3675 22.6679 11.166C21.3371 10.7998 16.0001 10.7998 16.0001 10.7998C16.0001 10.7998 10.6632 10.7998 9.3323 11.166C8.59795 11.3675 8.01962 11.9613 7.82335 12.7153C7.4668 14.0818 7.4668 16.9331 7.4668 16.9331C7.4668 16.9331 7.4668 19.7843 7.82335 21.151C8.01962 21.905 8.59795 22.4987 9.3323 22.7003C10.6632 23.0665 16.0001 23.0665 16.0001 23.0665C16.0001 23.0665 21.3371 23.0665 22.6679 22.7003C23.4022 22.4987 23.9805 21.905 24.1768 21.151C24.5335 19.7843 24.5335 16.9331 24.5335 16.9331C24.5335 16.9331 24.5335 14.0818 24.1768 12.7153Z"
                                    fill="white"
                                ></path>
                                <path d="M14.3999 19.8665V14.5332L18.6666 17.2L14.3999 19.8665Z" fill="#FF0000"></path>
                            </svg>
                        </span>
                        <span>
                            <svg width="32" height="33" viewBox="0 0 32 33" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path
                                    d="M0 16.6665C0 7.82995 7.16344 0.666504 16 0.666504C24.8366 0.666504 32 7.82995 32 16.6665C32 25.5031 24.8366 32.6665 16 32.6665C7.16344 32.6665 0 25.5031 0 16.6665Z"
                                    fill="#3171F6"
                                ></path>
                                <path
                                    fillRule="evenodd"
                                    clipRule="evenodd"
                                    d="M16.0002 5.99984C10.1091 5.99984 5.3335 10.4556 5.3335 15.9522C5.3335 19.0351 6.83597 21.7903 9.19473 23.6158V27.3332L12.8261 25.4565C13.8287 25.7477 14.8948 25.9046 16.0002 25.9046C21.8912 25.9046 26.6668 21.4488 26.6668 15.9522C26.6668 10.4556 21.8912 5.99984 16.0002 5.99984ZM9.87701 18.0804C10.6612 18.0804 11.3932 18.0759 12.125 18.0821C12.5362 18.0856 12.7584 18.2607 12.7962 18.5845C12.8442 18.9944 12.605 19.2664 12.1609 19.2714C11.3233 19.2809 10.4855 19.275 9.64768 19.275C9.40587 19.275 9.16349 19.2835 8.92244 19.2696C8.62187 19.2523 8.32787 19.1928 8.18415 18.8827C8.04006 18.5719 8.14015 18.293 8.33911 18.04C9.13968 17.0219 9.9412 16.0047 10.7422 14.9869C10.7898 14.9265 10.8357 14.8648 10.882 14.8043C10.833 14.7159 10.7554 14.7555 10.6949 14.7551C10.1336 14.7516 9.57215 14.7556 9.01082 14.7511C8.88254 14.7501 8.75044 14.7398 8.62701 14.7074C8.36663 14.6391 8.20854 14.4307 8.20644 14.182C8.20434 13.9329 8.35768 13.722 8.61749 13.6487C8.74025 13.6141 8.87282 13.6021 9.00111 13.6016C9.9252 13.5978 10.8493 13.5981 11.7734 13.6011C11.9367 13.6016 12.1011 13.6058 12.2597 13.6606C12.6101 13.7815 12.7643 14.1045 12.6219 14.4465C12.4978 14.7442 12.3001 14.9973 12.1027 15.2486C11.4252 16.1108 10.7452 16.9709 10.0663 17.8322C10.0136 17.899 9.96292 17.9676 9.87701 18.0804ZM14.0567 17.2472C14.0617 17.4255 14.1205 17.6652 14.2747 17.8732C14.6102 18.3257 15.2984 18.3243 15.6337 17.8723C15.9242 17.4805 15.9227 16.8304 15.6319 16.4389C15.4782 16.2321 15.273 16.1238 15.0169 16.1087C14.4487 16.0753 14.0509 16.5148 14.0567 17.2472ZM15.8889 15.3525C16.0151 15.1936 16.1404 15.0439 16.3538 15.0005C16.7609 14.9174 17.147 15.182 17.1525 15.596C17.1661 16.6319 17.161 17.668 17.1549 18.7041C17.1532 18.987 16.9789 19.2039 16.7239 19.2906C16.4567 19.3814 16.1783 19.3152 15.9998 19.09C15.9124 18.9797 15.875 18.9607 15.7531 19.0596C15.2812 19.4422 14.7489 19.5091 14.1735 19.3225C13.2505 19.023 12.8705 18.3038 12.7703 17.4228C12.6626 16.4766 12.9776 15.6645 13.8246 15.1666C14.5277 14.7532 15.2421 14.788 15.8889 15.3525ZM20.7838 17.1508C20.7824 17.416 20.8448 17.6634 21.0047 17.8783C21.3324 18.3189 22.0136 18.3224 22.348 17.8879C22.6494 17.4962 22.6504 16.8305 22.353 16.4346C22.1979 16.2282 21.9918 16.1217 21.7364 16.1082C21.1766 16.0785 20.7862 16.5065 20.7838 17.1508ZM19.4806 17.276C19.4411 15.9452 20.3142 14.9509 21.556 14.9127C22.8756 14.8721 23.8436 15.7594 23.883 17.0529C23.9229 18.3626 23.1194 19.2917 21.8803 19.416C20.5341 19.5509 19.4614 18.57 19.4806 17.276ZM19.0266 16.2455C19.0266 17.0484 19.0306 17.8513 19.025 18.6542C19.0218 19.1134 18.6166 19.4239 18.1809 19.3139C17.9192 19.2479 17.7236 18.9703 17.7231 18.6468C17.7211 17.2741 17.7223 15.9014 17.7223 14.5287C17.7223 14.287 17.7189 14.0451 17.7231 13.8035C17.7301 13.4051 17.9837 13.1465 18.3649 13.1428C18.7586 13.1389 19.0226 13.3985 19.0252 13.811C19.0302 14.6225 19.0266 15.434 19.0266 16.2455Z"
                                    fill="white"
                                ></path>
                            </svg>
                        </span>
                    </div>
                    <p className="fotter-heading">Tải ứng dụng trên điện thoại</p>
                    <div className="flex items-center ">
                        <div>
                            <img src="https://frontend.tikicdn.com/_desktop-next/static/img/footer/qrcode.png" width={70} alt="" />
                        </div>
                        <div className="ml-4">
                            <div className="mb-2">
                                <img src="https://frontend.tikicdn.com/_desktop-next/static/img/icons/appstore.png" width={100} alt="" />
                            </div>
                            <div>
                                <img src="https://frontend.tikicdn.com/_desktop-next/static/img/icons/playstore.png" width={100} alt="" />
                            </div>
                        </div>
                    </div>
                </div>

                <div className="col-1"></div>
            </div>
        </div>
    );
};

export default Footer;