package leesangho.adtechstudy.observation.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class DefaultControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public Mono<String> clientError(Exception exception) {
        return Mono.defer(() -> {
                log.error("client error! {}", exception.getMessage());
                return Mono.just(exception.getMessage());
            })
            .doOnNext(s -> log.info("client error doOnNext {}", s));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public Mono<String> serverError(Exception exception) {
        return Mono.defer(() -> {
                log.error("server error! {}", exception.getMessage());
                return Mono.just(exception.getMessage());
            })
            .doOnNext(s -> log.info("server error doOnNext {}", s));
    }
}
