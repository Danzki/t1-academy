package com.danzki;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@OpenAPIDefinition(
        info = @Info(
                title = "Limit application API",
                version = "1.0",
                description = "API Documentation",
                contact = @Contact(name = "danzki", email = "danzki@inbox.ru")
        ),
        servers = @Server(url = "http://localhost:8083", description = "Local ENV")
)
public class LimitApplication {
    public static void main(String[] args) {
        SpringApplication.run(LimitApplication.class);
    }
}
