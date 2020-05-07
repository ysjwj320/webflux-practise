package com.ysj.spring.cloud.example.controller;

import com.ysj.spring.cloud.example.domain.Account;
import com.ysj.spring.cloud.example.dto.AccountDto;
import com.ysj.spring.cloud.example.dto.ApiResponse;
import com.ysj.spring.cloud.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/controller/accounts")
    public Flux<AccountDto> getAllAccounts() {
        return accountService.getAll().map(AccountDto::of);
    }

    @PostMapping("/controller/signUp")
    public Mono<Object> signUp(@RequestBody Mono<Account> userMono) {
        return accountService.signUp(userMono).map(AccountDto::of).map(a -> (Object) a)
                .onErrorResume(e -> Mono.just(new ApiResponse(400, "User already exist", null)));
    }

}
