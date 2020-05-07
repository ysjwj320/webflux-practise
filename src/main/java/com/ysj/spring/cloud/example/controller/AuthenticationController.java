package com.ysj.spring.cloud.example.controller;

import com.ysj.spring.cloud.example.dto.ApiResponse;
import com.ysj.spring.cloud.example.dto.LoginRequest;
import com.ysj.spring.cloud.example.dto.LoginResponse;
import com.ysj.spring.cloud.example.exception.UserRequestException;
import com.ysj.spring.cloud.example.exception.RequestExceptionType;
import com.ysj.spring.cloud.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;

@RestController
public class AuthenticationController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/controller/login")
    public Mono login(@RequestBody Mono<LoginRequest> parameter) {
        return accountService.login(parameter).map(LoginResponse::new).map(r -> (Object)r)
                .onErrorResume(this::customException, this::handleException);
    }

    private boolean customException(Throwable t) {
        return UserRequestException.class.isInstance(t);
    }

    private Mono<ApiResponse> handleException(Throwable t) {
        UserRequestException e = (UserRequestException)t;
        ServerResponse.BodyBuilder bodyBuilder = badRequest();
        if (e.getType() == RequestExceptionType.USER_NOT_EXISTS) {
            return Mono.just(new ApiResponse(400, "User does not exist", null));
        } else {
            return Mono.just(new ApiResponse(400, "invalid user and password", null));
        }
    }

}
