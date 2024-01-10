package com.beeecommerce.model.dto;

import com.beeecommerce.model.core.SimpleAddress;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorDto implements Serializable, SimpleAddress {

    Long id;

    @NonNull
    String name;

    LocalDate registerAt;

    @NonNull
    Long userId;

    @NonNull
    Long wardId;

    @NonNull
    String street;

    String fullAddress;

    String addressNote;

    String receiverPhone;

    String image;

    private Long productCount;

    private String province;


    @Override
    public String getReceiverName() {
        return name;
    }

    @Override
    public String getNote() {
        return "";
    }

}