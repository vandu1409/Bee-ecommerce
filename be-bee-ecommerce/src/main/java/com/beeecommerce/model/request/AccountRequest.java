package com.beeecommerce.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String email;

    private String username;

    private String fullname;

    private String password;

    private String phoneNumber;

}
