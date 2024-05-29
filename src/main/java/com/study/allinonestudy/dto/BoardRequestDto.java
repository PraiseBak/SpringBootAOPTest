package com.study.allinonestudy.dto;

import com.study.allinonestudy.entity.Board;
import com.study.allinonestudy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@Builder
public class BoardRequestDto {
    private String title;

    private String content;

    public BoardRequestDto(Board board){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(board,this);
    }

}
