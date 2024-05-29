package com.study.allinonestudy.controller;

import com.study.allinonestudy.dto.BoardRequestDto;
import com.study.allinonestudy.entity.Board;
import com.study.allinonestudy.entity.User;
import com.study.allinonestudy.service.BoardService;
import com.study.allinonestudy.service.ImageService;
import com.study.allinonestudy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final UserService userService;
    private final BoardService boardService;
    private final ImageService imageService;

    @GetMapping("/")
    public String boardList(Model model,@RequestParam(name = "page") int page) {
        System.out.println(page);
        model.addAttribute("boards", boardService.findAllBoards(page));
        return "board/boardList";
    }

    @GetMapping("/write")
    public String boardWriteForm() {
        return "board/boardWrite";
    }

    @PostMapping(value = "/", consumes = "multipart/form-data")
    public String boardWrite(Authentication authentication, @RequestPart(name = "board") @Validated Board board,@RequestPart(name = "file") MultipartFile multipartFile) {
        BoardRequestDto boardRequestDto = new BoardRequestDto(board);
        board = new Board(boardRequestDto);

        String imageSrc = imageService.saveImage(multipartFile);
        System.out.println(imageSrc);
        System.out.println(board.toString()  );

        boardService.saveBoard(board, authentication.getName());
        return "redirect:/board/";
    }

    @GetMapping("/update/{id}")
    public String boardUpdateForm(@PathVariable Long id,Board board,Model model) {
        model.addAttribute("board", boardService.findById(id));
        return "board/boardUpdate";
    }

    @PutMapping("/{id}")
    public String boardUpdate(@PathVariable Long id, @ModelAttribute @Validated Board board,Authentication authentication,MultipartFile multipartFile) {
        BoardRequestDto boardRequestDto = new BoardRequestDto(board);
        Board updateBoard = new Board(boardRequestDto);
        boardService.updateBoard(id,authentication.getName(),updateBoard);

        return "redirect:/board/";
    }

    @DeleteMapping("/delete/{id}")
    public String boardDelete(@PathVariable Long id,Authentication authentication) {
        boardService.deleteBoard(id,authentication.getName());
        return "redirect:/board/";
    }
}

