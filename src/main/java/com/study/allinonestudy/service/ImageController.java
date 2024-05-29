package com.study.allinonestudy.service;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/img")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * 이미지를 가져오는 url
     *
     * @param fileName
     * @param response
     */
    @GetMapping("/get.cf")
    public void getFile(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        response.setContentType("application/jpeg");

        String url = "file:///" + imageService.getUploadPath();
        URL fileUrl = new URL(url + File.separator + fileName);
        try {
            IOUtils.copy(fileUrl.openStream(), response.getOutputStream());
        } catch (FileNotFoundException e) {
            throw new NoSuchElementException("해당 파일을 찾을 수 없습니다");
        }
    }
}

