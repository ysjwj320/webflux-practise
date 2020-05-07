package com.ysj.spring.cloud.example;

import com.ysj.spring.cloud.example.domain.Account;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorizationServiceApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationServiceApplicationTests.class);

    @Test
    public void contextLoads() {
        Account account = new Account();
        account.setUsername("yanshujie");
        log.info("--------------");
        Mono<ClientResponse> accountMono = WebClient.builder().baseUrl("http://localhost:8080").build()
                .post().uri("/signUp").contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(account))
                .exchange();
        accountMono.doOnSuccess(s -> log.info("response status: {}", s.statusCode()))
                .doOnError(e -> log.info("", e));
        log.info("===============");
//        accountMono.log().map(Account::toString).doOnSuccess(System.out::println);
    }

}
