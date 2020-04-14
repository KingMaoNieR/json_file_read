package com.longshare.work.jsonread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)

public class JsonreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonreadApplication.class, args);
    }

}
