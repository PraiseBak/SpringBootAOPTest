package com.study.allinonestudy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Entity
public class User {
    @Id
    private Long id;

    @NotNull
    @Size(min = 2,max = 20)
    private String username;

    @Size(min = 0,max = 400)
    private String selfIntroduce;

    @NotNull
    private String password;

    private String profileImgSrc;

}
