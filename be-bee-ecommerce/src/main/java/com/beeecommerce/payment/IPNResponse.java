package com.beeecommerce.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class IPNResponse {
    private String Message;

    private String RspCode;
}
