package com.ysj.spring.cloud.example.exception.handler;

import org.springframework.core.ResolvableType;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 在方法级别上处理业务中的异常
 */
public abstract class AbstractExceptionHandler<T extends Throwable> implements IExceptionHandler {

    protected final Class<T> exceptionClass;

    public AbstractExceptionHandler() {
        ResolvableType resolvableType = ResolvableType.forClass(getClass());
        this.exceptionClass = (Class<T>) resolvableType.getSuperType().getGeneric(0).resolve();
    }

    @Override
    public Predicate<? super Throwable> predicate() {
        return t -> exceptionClass.isInstance(t);
    }

    @Override
    public <U> Function<? super Throwable, ? extends Mono<? extends U>> fallback() {
        return t -> Mono.empty();
    }


}
