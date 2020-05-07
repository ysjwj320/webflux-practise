package com.ysj.spring.cloud.example.service;

import com.ysj.spring.cloud.example.domain.Account;
import com.ysj.spring.cloud.example.dto.LoginRequest;
import com.ysj.spring.cloud.example.exception.UserRequestException;
import com.ysj.spring.cloud.example.exception.RequestExceptionType;
import com.ysj.spring.cloud.example.repository.AccountRepository;
import com.ysj.spring.cloud.example.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class AccountService {

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    public Flux<Account> getAll() {
        return accountRepository.findAll();
    }

    public Mono<Account> signUp(Mono<Account> userMono) {
        return userMono.flatMap(user -> accountRepository.findByUsername(user.getUsername())
                .flatMap(dbUser -> Mono.<Account>error(new UserRequestException(RequestExceptionType.USER_EXISTS)))
                .switchIfEmpty(accountRepository.save(initAccount(user))));
    }

    public Mono<String> login(Mono<LoginRequest> loginRequest) {
        return loginRequest.flatMap(login -> accountRepository.findByUsername(login.getUsername())
                .flatMap(user -> validPassword(user, login))
                .switchIfEmpty(Mono.error(new UserRequestException(RequestExceptionType.USER_NOT_EXISTS))));
    }

    private Account initAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setCreateTime(new Date());
        return account;
    }

    private Mono<String> validPassword(Account account, LoginRequest login) {
        if (passwordEncoder.matches(login.getPassword(), account.getPassword())) {
            return Mono.just(tokenProvider.generateToken(account));
        } else {
            return Mono.error(new UserRequestException(RequestExceptionType.USER_PASSWORD_ERROR));
        }
    }

}
