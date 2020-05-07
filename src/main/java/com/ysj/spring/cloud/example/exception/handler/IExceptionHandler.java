package com.ysj.spring.cloud.example.exception.handler;

import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 在方法级别上处理业务中的异常
 */
public interface IExceptionHandler {

    Predicate<? super Throwable> predicate();

    <T> Function<? super Throwable, ? extends Mono<? extends T>> fallback();

}
