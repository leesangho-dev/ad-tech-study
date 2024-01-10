package leesangho.adtechstudy.webflux.controller;

import java.util.NoSuchElementException;
import leesangho.adtechstudy.webflux.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse<Void> notFoundError(Exception exception) {
        return BaseResponse.of(HttpStatus.NOT_FOUND, exception.getMessage(), null);
    }

    @ExceptionHandler({IllegalArgumentException.class,
        IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Void> clientError(Exception exception) {
        return BaseResponse.clientErrorOf(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<Void> serverError(Exception exception) {
        return BaseResponse.serverErrorOf(exception.getMessage());
    }
}
