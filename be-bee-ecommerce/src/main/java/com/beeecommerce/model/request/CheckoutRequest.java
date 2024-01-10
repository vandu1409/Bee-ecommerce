package com.beeecommerce.model.request;

import com.beeecommerce.constance.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {

    @NonNull
    List<Long> cartId;

    @NonNull
    List<Long> voucherApplied;

    @NonNull
    Long addressId;

    PaymentMethod paymentMethod;

}
