package com.ysj.spring.cloud.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public  final class ApiResponse {
    @Builder.Default
    private int code = 200;
    private String message;
    private String content;
}