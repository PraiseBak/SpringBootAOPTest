package com.study.allinonestudy.service;

import com.study.allinonestudy.entity.Board;
import com.study.allinonestudy.entity.User;
import com.study.allinonestudy.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardService {

    private BoardRepository boardRepository;

    private UserService userService;

    public List<Board> findAllBoards() {

    }

    @Transactional
    public void saveBoard(Board board, String username) {
        User user = userService.findByUsername(username);
        board.setUser(user);
        boardRepository.save(board);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 게시물을 찾을 수 없습니다"));
    }

    @Transactional
    public void updateBoard(Long id, String username,Board updateBoard) {
        Board board = findById(id);
        checkAccess(username,board);
        board.updateBoard(updateBoard);
    }


    public boolean isSameUser(String username,Board board){
        return username.equals(board.getUser().getUsername());
    }

    public void checkAccess(String username,Board board){
        if(!isSameUser(username,board)) throw new BadCredentialsException("권한이 부족합니다");
    }


    public void deleteBoard(Long id, String username) {
        Board board = findById(id);
        checkAccess(username,board);
        boardRepository.deleteById(id);
    }
}
