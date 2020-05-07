package com.ysj.spring.cloud.example.handler;

import com.ysj.spring.cloud.example.dto.ApiResponse;
import com.ysj.spring.cloud.example.dto.LoginRequest;
import com.ysj.spring.cloud.example.dto.LoginResponse;
import com.ysj.spring.cloud.example.exception.UserRequestException;
import com.ysj.spring.cloud.example.exception.RequestExceptionType;
import com.ysj.spring.cloud.example.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Service
public class AuthenticationHandler {

    @Autowired
    private AccountService accountService;

    public Mono<ServerResponse> login(ServerRequest request) {
        Mono<LoginRequest> loginRequest = request.bodyToMono(LoginRequest.class);
        return login(loginRequest);
    }

    public Mono<ServerResponse> login(Mono<LoginRequest> loginRequest) {
        return accountService.login(loginRequest).map(LoginResponse::new)
                .flatMap(s -> ok().contentType(APPLICATION_JSON).body(fromValue(s)))
                .onErrorResume(this::customException, this::handleException);
    }

    private boolean customException(Throwable t) {
        return UserRequestException.class.isInstance(t);
    }

    private Mono<ServerResponse> handleException(Throwable t) {
        UserRequestException e = (UserRequestException)t;
        ServerResponse.BodyBuilder bodyBuilder = badRequest();
        if (e.getType() == RequestExceptionType.USER_NOT_EXISTS) {
            return bodyBuilder.body(fromValue(new ApiResponse(400, "User does not exist", null)));
        } else {
            return bodyBuilder.body(fromValue(new ApiResponse(400, "invalid user and password", null)));
        }
    }

}

