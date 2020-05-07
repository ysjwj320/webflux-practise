package com.ysj.spring.cloud.example.handler;

import com.ysj.spring.cloud.example.domain.Account;
import com.ysj.spring.cloud.example.dto.AccountDto;
import com.ysj.spring.cloud.example.dto.ApiResponse;
import com.ysj.spring.cloud.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class AccountHandler {

    @Autowired
    public AccountService accountService;

    public Mono<ServerResponse> getAccounts(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON).body(accountService.getAll().map(AccountDto::of), AccountDto.class);
    }

    public Mono<ServerResponse> signUp(ServerRequest request) {
        Mono<Account> userMono = request.bodyToMono(Account.class);
        return signUp(userMono);
    }

    public Mono<ServerResponse> signUp(Mono<Account> userMono) {
        return accountService.signUp(userMono).map(AccountDto::of)
                .flatMap(a -> ok().contentType(APPLICATION_JSON).body(fromValue(a)))
                .onErrorResume(e -> badRequest().body(fromValue(new ApiResponse(400, "User already exist", null))));
    }

    public Mono<ServerResponse> test(ServerRequest request) {
        return sayHello(request)
                .flatMap(s -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).syncBody(s))
                .onErrorResume(e -> Mono.just("Error " + e.getMessage()).flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN).syncBody(s)));
    }

    public Mono<String> sayHello(ServerRequest request) {
        return Mono.just("i am say hello");
    }

}
