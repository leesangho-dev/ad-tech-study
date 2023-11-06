package leesangho.adtechstudy.mvcapplication.controller;

import leesangho.adtechstudy.mvcapplication.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler({IllegalArgumentException.class,
            IllegalStateException.class, NoSuchElementException.class,
            ServletRequestBindingException.class})
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
