package com.ysj.spring.cloud.example.repository;

import com.ysj.spring.cloud.example.domain.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, Long> {

    Mono<Account> findByUsername(String username);

}
