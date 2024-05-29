package com.study.allinonestudy;

import com.praiseutil.timelog.config.AppConfig;
import jakarta.persistence.EntityListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@SpringBootApplication
@Import(AppConfig.class)
public class AllInOneStudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(AllInOneStudyApplication.class, args);
    }
}
