package com.study.allinonestudy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ImageService {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    @Value(value = "${upload.path}")
    private String uploadPath = "";

    public String saveImage(MultipartFile multipartFile) {
        String fileName = LocalDateTime.now().toString().replace(" ","_") + "_" + Math.random() + "";
        File file = new File(uploadPath +"/" + fileName + ".png");
        try {
            //10MB제한
            if(multipartFile.getSize() > MAX_FILE_SIZE){
                throw new RuntimeException("파일 생성에 실패하였습니다. [용량 제한 10MB 초과]");
            }
            multipartFile.transferTo(file);
            return file.getName();
        } catch (IOException e) {
            throw new RuntimeException("파일 생성에 실패하였습니다.");
        }


    }

    public String getUploadPath() {
        return uploadPath;
    }
}
