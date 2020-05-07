package com.ysj.spring.cloud.example.exception.handler;

import com.google.common.collect.Sets;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.Set;

@Lazy
@Component
public class ExceptionHandlerFactory implements ApplicationContextAware {
    private final Set<IExceptionHandler> handlers = Sets.newLinkedHashSet();

    public <T extends Throwable> Flux<IExceptionHandler> handlers(T e) {
        return Flux.fromIterable(handlers).filter(h -> h.predicate().test(e));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        Map<String, IExceptionHandler> beanMaps = applicationContext.getBeansOfType(IExceptionHandler.class);
        beanMaps.values().parallelStream().sorted(OrderComparator.INSTANCE).forEach(handlers::add);
    }

}
