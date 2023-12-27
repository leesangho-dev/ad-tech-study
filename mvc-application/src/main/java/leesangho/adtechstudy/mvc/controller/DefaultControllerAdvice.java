package leesangho.adtechstudy.mvc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import leesangho.adtechstudy.mvc.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.filter.ServerHttpObservationFilter;

@Slf4j
@RestControllerAdvice
public class DefaultControllerAdvice {

  @ExceptionHandler({NoSuchElementException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public BaseResponse<Void> notFoundError(HttpServletRequest request, Exception exception) {
    log.error("Not Found Error");
    ServerHttpObservationFilter.findObservationContext(request)
        .ifPresent(context -> context.setError(exception));
    return BaseResponse.of(HttpStatus.NOT_FOUND, exception.getMessage(), null);
  }

  @ExceptionHandler({IllegalArgumentException.class,
      IllegalStateException.class, ServletException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseResponse<Void> clientError(HttpServletRequest request, Exception exception) {
    log.error("client error");
    ServerHttpObservationFilter.findObservationContext(request)
        .ifPresent(context -> context.setError(exception));
    return BaseResponse.clientErrorOf(exception.getMessage());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse<Void> serverError(Exception exception) {
    return BaseResponse.serverErrorOf(exception.getMessage());
  }
}
