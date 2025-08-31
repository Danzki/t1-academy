package com.danzki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MainProto {
    public static void main(String[] args) {
        SpringApplication.run(MainProto.class, args);
    }
}
