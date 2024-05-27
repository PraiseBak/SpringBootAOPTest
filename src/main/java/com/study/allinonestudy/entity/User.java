package com.study.allinonestudy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.study.allinonestudy.config.GrantedAuthorityDeserializer;
import com.study.allinonestudy.config.GrantedAuthoritySerializer;
import com.study.allinonestudy.dto.UserRequestDto;
import com.study.allinonestudy.helper.Role;
import com.study.allinonestudy.helper.UserRequestValidationGroup;
import jakarta.annotation.Nullable;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    //null이어도 비어있으면안됨
    @Size(min = 2,max = 400)
    private String selfIntroduce;

    @Size(min = 2,max = 400)
    private String profileImgSrc;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @ColumnDefault("'USER'")
    private List<Role> roles = new ArrayList<>(List.of(Role.USER));

    public User(UserRequestDto userDto){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(userDto,this);
        this.roles = new ArrayList<>(List.of(Role.USER));
    }

    @Override
    @JsonDeserialize(using = GrantedAuthorityDeserializer.class)
    @JsonSerialize(using = GrantedAuthoritySerializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    public List<Role> getRoles() {
        return roles;
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
    public boolean isAccountNonExpired()  {
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


