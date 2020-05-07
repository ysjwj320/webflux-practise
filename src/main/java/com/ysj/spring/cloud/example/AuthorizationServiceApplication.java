package com.ysj.spring.cloud.example;

import com.mongodb.MongoClientOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthorizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServiceApplication.class, args);
    }

    @Bean
    public MongoClientOptions mongoOptions() {
        return MongoClientOptions.builder().socketTimeout(20000).build();
    }

}
