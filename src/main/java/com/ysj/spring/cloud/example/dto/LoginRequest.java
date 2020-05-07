package com.ysj.spring.cloud.example.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String password;
    private String username;

}
