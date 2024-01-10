package com.beeecommerce.service.impl;

import com.beeecommerce.constance.OrderStatus;
import com.beeecommerce.constance.PaymentMethod;
import com.beeecommerce.constance.TransactionStatus;
import com.beeecommerce.constance.TransactionType;
import com.beeecommerce.entity.*;
import com.beeecommerce.exception.auth.AuthorizationException;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.OutOfStockException;
import com.beeecommerce.exception.common.VoucherNotApplicableException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.exception.core.ApplicationRuntimeException;
import com.beeecommerce.facade.GHNFacade;
import com.beeecommerce.facade.VoucherAppliedFacade;
import com.beeecommerce.mapper.OrderMapper;
import com.beeecommerce.model.core.SystemData;
import com.beeecommerce.model.dto.OrderDto;
import com.beeecommerce.model.request.CheckoutRequest;
import com.beeecommerce.repository.*;
import com.beeecommerce.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final AccountRepository accountRepository;

    private final OrderMapper orderMapper;

    private final VoucherService voucherService;

    private final VoucherAppliedFacade voucherAppliedFacade;

    private final CartService cartService;

    private final AccountService accountService;

    private final GHNFacade ghnFacade;

    private final AddressService addressService;

    private final VoucherAppliedRepository voucherAppliedRepository;

    private final SystemData systemData;

    private final CartRepository cartRepository;

    private final VNPayService vnPayService;

    private final TransactionRepository transactionRepository;


    @Override
    public Page<OrderDto> getAll(Pageable pageable) {
        return orderRepository
                .findAll(pageable)
                .map(orderMapper);
    }

    @Override
    @Transactional
    public OrderDto insert(CheckoutRequest checkoutRequest) throws ApplicationException, VoucherNotApplicableException {

        Order order = new Order();
        Long totalPrice = 0l;

        Account account = accountService.getAuthenticatedAccount();

        if (account == null) {
            throw new AuthorizationException("Vui lòng đăng nhập!");
        }

        List<Cart> carts = cartService
                .getCartByIds(checkoutRequest.getCartId());

        if (carts != null && !carts.isEmpty()) {

            Vendor firstVendor = carts.get(0).getProduct().getVendors();

            boolean allCartsHaveSameVendor = carts.stream()
                    .allMatch(cart -> cart.getProduct().getVendors().getId().equals(firstVendor.getId()));

            if (!allCartsHaveSameVendor) {
                throw new ApplicationException("Các giỏ hàng không cùng 1 shop không thể đặt hàng!");
            }
        }


        List<OrderDetail> orderDetails = new ArrayList<>();

        // Map các cart thành orderDetail
        for (Cart cart : carts) {
            // Check xem cart có thuộc user đang đăng nhập hay không
            if (!cart.getUser().getId().equals(account.getId())) {
                throw new AuthorizationException("Bạn không có quyền thực hiện hành động này!");
            }
            // Check xem còn đủ hàng để bán hay không
            if (cart.getClassify().getInventory() < cart.getQuantity()) {
                throw new OutOfStockException("Sản phẩm " + cart.getProduct().getName() + " đã hết hàng!");
            }

            // Thêm vào danh sách orderDetail
            orderDetails.add(
                    OrderDetail
                            .builder()
                            .classify(cart.getClassify())
                            .quantity(cart.getQuantity())
                            .sellPrice(cart.getClassify().getSellPrice())
                            .quantity(cart.getQuantity())
                            .order(order)
                            .build()
            );

            // Trừ số lượng hàng trong kho
            cart.getClassify()
                    .setInventory(
                            cart.getClassify().getInventory() - cart.getQuantity()
                    );

            totalPrice += cart.getClassify().getSellPrice() * cart.getQuantity();
        } // End for

        // Lấy address
        Address address = addressService.getById(checkoutRequest.getAddressId());

        order.setPaymentMethod(checkoutRequest.getPaymentMethod());
        order.setStatus(checkoutRequest.getPaymentMethod().equals(PaymentMethod.BALANCE)
                ? OrderStatus.PAID
                : OrderStatus.PENDING);
        order.setUser(account);
        order.setOrderDetails(orderDetails);
        order.setAddress(address);
        order.setCreateAt(LocalDate.now());
        order.setDeliveryAt(null);
        order.setShippingFee(
                ghnFacade.calculateShippingFee(
                        order.getAddress().getId(),
                        order.getVendor().getAddress().getWard().getLongCode(),
                        orderDetails.stream().map(i -> i.getProduct().getId()).toList()
                ));
        order.setTotalPrice(totalPrice);


        Long discountedPrice = 0l;
        // Lấy danh sách voucher
        if (checkoutRequest.getVoucherApplied() != null) {
            List<Voucher> vouchers = voucherService
                    .getAllVoucherByIds(checkoutRequest.getVoucherApplied());
            List<VoucherApplied> vouchersApplied = voucherAppliedFacade
                    .appliedVoucher(vouchers, order);

            discountedPrice = vouchersApplied.stream().mapToLong(VoucherApplied::getDiscount).sum();

            order.setVouchersApplied(vouchersApplied);
        }

        // Áp dụng mã giảm giá


        processPayment(order, discountedPrice);
        // Lưu order
        Order dbOrder = orderRepository.save(order);

        cartRepository.deleteAll(carts);

        return orderMapper.apply(dbOrder);
    }


    public void processPayment(Order order, Long discount) throws ApplicationException {
        Account account = order.getUser();

        if (order.getPaymentMethod().equals(PaymentMethod.BALANCE)) {
            Long total = order.getTotalPrice() - discount + order.getShippingFee();
            if (account.getBalance() < total) {
                throw new ApplicationException("Số tiền trong tài khoản của bạn không đủ!");

            }

            account.setBalance(account.getBalance() - total);
            accountRepository.save(account);

        }
    }


    @Override
    @Transactional
    public void changeStatus(Long orderId) throws ApplicationException {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ApplicationRuntimeException("Không tìm thấy đơn hàng!"));

        Account account = accountService.getAuthenticatedAccount();

        if (!Objects.equals(account.getVendor().getId(),order.getVendor().getId())) {
            System.err.println(account.getVendor().getId() + " - "+order.getVendor().getId());
            throw new ApplicationException("Bạn không có quyền thực hiện hành động này!");
        }

        Double percent = systemData.getDoubleValue("order_fee");

        Double requiredShopBalance = order.getTotalPrice() * percent;

        if (account.getBalance() < requiredShopBalance && order.getStatus().equals(OrderStatus.PENDING) ||
                account.getBalance() < requiredShopBalance && order.getStatus().equals(OrderStatus.PAID)) {
            throw new ApplicationException("Số tiền trong tài khoản không đủ để xác nhận đơn hàng!");
        }


        OrderStatus currentStatus = order.getStatus();

        if (currentStatus == OrderStatus.PENDING || currentStatus == OrderStatus.PAID) {

            if (currentStatus == OrderStatus.PENDING && order.getPaymentMethod().equals(PaymentMethod.VNPAY)) {
                throw new ApplicationException("Đơn hàng này chưa được thanh toán không thể xác nhận!");
            }


            if (currentStatus == OrderStatus.PENDING) {
                account.setBalance((long) (account.getBalance() - requiredShopBalance));
            }

            order.setStatus(OrderStatus.CONFIRMED);

        } else if (currentStatus == OrderStatus.CONFIRMED) {
            order.setStatus(OrderStatus.DELIVERING);
        }

        if(currentStatus==OrderStatus.DELIVERING){
            throw new ApplicationException("Đơn hàng đang vận chuyển đến người mua! Bạn không thể chuyển trạng thái!");
        }

        accountRepository.save(account);
        orderRepository.save(order);

    }

    // phía client
    @Override
    public void confirmed(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ApplicationRuntimeException("Không tìm thấy đơn hàng!"));
        OrderStatus currentStatus = order.getStatus();
        Account account = accountService.getAuthenticatedAccount();

        if (Objects.equals(account.getId(),order.getUser().getId())) {
            order.setStatus(OrderStatus.COMPLETED);
            if (currentStatus == OrderStatus.DELIVERING) {
                if (order.getPaymentMethod().equals(PaymentMethod.VNPAY) || order.getPaymentMethod().equals(PaymentMethod.BALANCE)) {
                    Long totalPrice = order.getTotalPrice() + order.getShippingFee() - order.getDiscount();

                    order.setDeliveryAt(LocalDate.now());
                    account.setBalance(account.getBalance() + totalPrice);
                }
            }
        }
        orderRepository.save(order);
        accountRepository.save(account);

    }

    @Override
    public void cancelClient(Long orderId) throws ApplicationException {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ApplicationException("Không tìm thấy đơn hàng!"));

        Account account = accountService.getAuthenticatedAccount();

        if (!Objects.equals(order.getUser().getId(),account.getId())) {
            throw new ApplicationException("Bạn không có quyền thực hiện hành động này!");
        }


        if (!order.getStatus().equals(OrderStatus.PENDING) && !order.getStatus().equals(OrderStatus.PAID)) {
            throw new ApplicationException("Hàng đã được vẫn chuyển không thể hủy!");
        }


        if (!order.getPaymentMethod().equals(PaymentMethod.COD)) {
            if (order.getStatus().equals(OrderStatus.PAID)) {
                Long totalPrice = order.getTotalPrice() + order.getShippingFee() - order.getDiscount();

                account.setBalance(account.getBalance() + totalPrice);
            }
        }

        order.setStatus(OrderStatus.CANCELLED);

        accountRepository.save(account);
        orderRepository.save(order);

    }

    @Override
    public void cancelAdmin(Long orderId) throws ApplicationException {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ApplicationException("Không tìm thấy đơn hàng!"));

        Account account = accountService.getAuthenticatedAccount();

        if (!Objects.equals(order.getVendor().getId(),account.getId()) ) {
            throw new ApplicationException("Bạn không có quyền thực hiện hành động này!");
        }


        if (!order.getStatus().equals(OrderStatus.PENDING) && !order.getStatus().equals(OrderStatus.PAID)) {
            throw new ApplicationException("Hàng đã được vẫn chuyển không thể hủy!");
        }


        if (!order.getPaymentMethod().equals(PaymentMethod.COD)) {
            if (order.getStatus().equals(OrderStatus.PAID)) {
                Long totalPrice = order.getTotalPrice() + order.getShippingFee() - order.getDiscount();

                Account customer = order.getUser();

                customer.setBalance(customer.getBalance() + totalPrice);

                accountRepository.save(customer);
            }
        }

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

    }

    @Override
    public OrderDto findById(Long id) {
        return orderRepository.findById(id).map(orderMapper).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy đơn đặt  hàng!"));
    }

    public Page<OrderDto> findByUser(Long userId, Pageable pageable) {
        return orderRepository.findByUser(userId, pageable).map(orderMapper);
    }

    public Page<OrderDto> findByVendor(Long vendorId, Pageable pageable) {
        return orderRepository.findByVendor(vendorId, pageable).map(orderMapper);
    }

    @Override
    public Page<OrderDto> findByUserLogged(Pageable pageable) {
        Account account = accountService.getAuthenticatedAccount();

        return findByUser(account.getId(), pageable);
    }

    @Override
    public Page<OrderDto> findByVendorLogged(Pageable pageable) {
        Account account = accountService.getAuthenticatedAccount();

        return findByVendor(account.getVendor().getId(), pageable);
    }

    @Override
    public String paymentVnpay(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Không tìm  thấy đơn hàng!"));

        Transaction transaction = new Transaction();
        transaction.setAmount(order.getAmount());
        transaction.setType(TransactionType.PAYORDER);
        transaction.setPaymentMethod(PaymentMethod.VNPAY);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setOrderId(orderId);

        String link = vnPayService.createPaymentUrl(
                transactionRepository.save(transaction)
        );

        return link;
    }

}
