package com.example.PrintAppPOC.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails{

    @Id
    @NotBlank(message = "Number cannot be blank")
    private String mobileNumber;
    @Size(min = 2,max = 20)
    private String name;
    private String icon;
    @OneToOne(fetch = FetchType.LAZY)
    private Store store;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return "$2a$10$5IL6uAX/2zzPiqcdADz8he8eIgX7okPNP8D.28oc3aN1kGRkUq5PS";
    }


    @Override
    public String getUsername() {
        return this.getMobileNumber();
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
        return true;
    }
}
