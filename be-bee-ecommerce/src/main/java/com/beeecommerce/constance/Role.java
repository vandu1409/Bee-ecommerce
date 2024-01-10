package com.beeecommerce.constance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum Role {
    CUSTOMER(
            Set.of(
                    Permission.CREATE_ORDER
            )
    ),
    VENDOR(
            Set.of(
                    Permission.ADD_PRODUCT,
                    Permission.DELETE_PRODUCT
            )
    ),

    ADMIN(
            Set.of(
                    Permission.ADD_PRODUCT,
                    Permission.DELETE_PRODUCT,
                    Permission.LOCK_ACCOUNT
            )
    );


    private final Set<Permission> roles;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles
                .stream()
                .map(
                        permission -> new SimpleGrantedAuthority("ROLE" + permission.name())
                )
                .toList();
    }

}
