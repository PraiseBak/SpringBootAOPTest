package com.study.allinonestudy.config;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;


@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    @Value(value = "${upload.path}")
    private String uploadPath = "";

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 업로드 경로에 대한 File 객체 생성

        System.out.println("uploadPath" + uploadPath);

        File uploadFolder = new File(uploadPath);

        // 업로드 폴더가 없는 경우 폴더 생성
        if (!uploadFolder.exists()) {
            boolean created = uploadFolder.mkdirs();

            if (!created) {
                throw new IllegalStateException("Failed to create upload folder.");
            }
        }


        registry
            .addResourceHandler("/img/")
            .addResourceLocations("file:///" + uploadPath);
    }
}
