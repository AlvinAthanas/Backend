package com.example.cms_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CmsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsBackendApplication.class, args);
    }

}
