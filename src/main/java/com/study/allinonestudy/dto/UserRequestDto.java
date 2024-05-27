package com.study.allinonestudy.dto;

import com.study.allinonestudy.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@Builder
public class    UserRequestDto {
    private String username;

    private String password;

    private String selfIntroduce;

    private String profileImgSrc;

    public UserRequestDto(User user){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(user,this);
    }

}
