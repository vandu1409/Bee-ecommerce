package com.beeecommerce.entity;

import com.beeecommerce.constance.ConstraintName;
import com.beeecommerce.constance.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account",
        uniqueConstraints = {
                @UniqueConstraint(name = ConstraintName.ACCOUNT_USERNAME_UNIQUE, columnNames = "username"),
                @UniqueConstraint(name = ConstraintName.ACCOUNT_EMAIL_UNIQUE, columnNames = "email"),
                @UniqueConstraint(name = ConstraintName.ACCOUNT_PHONE_NUMBER_UNIQUE, columnNames = "phone_number")
        }
)
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    @Nationalized
    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @Nationalized
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Nationalized
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Nationalized
    @Column(name = "fullname", nullable = false, length = 100)
    private String fullname;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "avatar", length = 250)
    private String avatar;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "last_login_time")
    private Instant lastLoginTime;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "balance")
    private Long balance = 0L;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Vendor> vendors = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Voucher> vouchers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Wishlist> wishlists = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole().getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public String getSecretKey() {
        return this.getPassword().replaceAll("\\W", "");
    }


    public Vendor getVendor() {
        return vendors.isEmpty() ? null : vendors.get(0);
    }
}