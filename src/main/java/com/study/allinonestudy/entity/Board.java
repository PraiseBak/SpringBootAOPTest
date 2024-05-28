package com.study.allinonestudy.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.study.allinonestudy.config.GrantedAuthorityDeserializer;
import com.study.allinonestudy.config.GrantedAuthoritySerializer;
import com.study.allinonestudy.dto.BoardRequestDto;
import com.study.allinonestudy.dto.UserRequestDto;
import com.study.allinonestudy.helper.Role;
import com.study.allinonestudy.helper.UserRequestValidationGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min=2,max=2000)
    private String content;

    @NotNull
    @NotBlank
    @Size(min=2,max=40)
    private String title;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private User user;

    @CreatedDate
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Board(BoardRequestDto boardRequestDto){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(boardRequestDto,this);
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void updateBoard(Board updateBoard) {
        this.title = updateBoard.getTitle();
        this.content = updateBoard.getContent();
    }
}


