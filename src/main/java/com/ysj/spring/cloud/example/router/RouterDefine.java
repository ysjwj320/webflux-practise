package com.ysj.spring.cloud.example.router;

import com.ysj.spring.cloud.example.handler.AccountHandler;
import com.ysj.spring.cloud.example.handler.AuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterDefine {

    /**
     * authentication router
     */
    @Bean
    public RouterFunction<ServerResponse> authenticationRouter(@Autowired AuthenticationHandler authenticationHandler) {
        return route(POST("/api/login").and(accept(APPLICATION_JSON)), authenticationHandler::login);
    }

    @Bean
    public RouterFunction<ServerResponse> accountRouter(@Autowired AccountHandler accountHandler) {
        return route(POST("/api/signUp").and(accept(APPLICATION_JSON)), accountHandler::signUp)
                .andRoute(GET("/api/users"), accountHandler::getAccounts);
    }

}
