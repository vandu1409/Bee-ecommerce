package com.beeecommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.beeecommerce.entity.Account}
 */
@Data
@SuperBuilder
@AllArgsConstructor
public class AccountDto implements Serializable {

    private String email;

    private String username;

    private String fullname;

    private String phoneNumber;

    private LocalDate updateAt;

    private Boolean isActive;

    private Instant lastLoginTime;

    private String avatar;

    private VendorDto vendor;

}