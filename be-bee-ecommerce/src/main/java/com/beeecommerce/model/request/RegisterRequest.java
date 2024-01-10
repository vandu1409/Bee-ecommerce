package com.beeecommerce.model.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NonNull
    private String email;

    @NonNull
    private String username;

    @NonNull
    private String fullname;

    @NonNull
    private String phoneNumber;

    @NonNull
    private String password;

    @NonNull
    private String confirmPassword;
}
