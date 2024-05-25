package com.study.allinonestudy.entity;

import com.study.allinonestudy.dto.UserRequestDto;
import com.study.allinonestudy.helper.Role;
import com.study.allinonestudy.helper.UserRequestValidationGroup;
import jakarta.annotation.Nullable;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class User implements UserDetails {
    @Id
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 2,max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must consist of letters and numbers only.")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 8,max = 20,groups = UserRequestValidationGroup.class)
    private String password;

    @Size(min = 0,max = 400)
    @NotBlank
    private String selfIntroduce;

    @NotBlank
    private String profileImgSrc;

    private List<Role> roles = List.of(Role.USER);

    public User(UserRequestDto userDto){


    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
