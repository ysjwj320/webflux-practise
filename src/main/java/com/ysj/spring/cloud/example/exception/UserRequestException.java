package com.ysj.spring.cloud.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserRequestException extends RuntimeException {

    private final RequestExceptionType type;

    public UserRequestException(RequestExceptionType type, String message) {
        super(message);
        this.type = type;
    }

}
