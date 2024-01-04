package leesangho.adtechstudy.mvc.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  @ApiResponse(responseCode = "404", description = "찾을 수 없음", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"code\":404,\"message\":\"요소를 찾을 수 없습니다.\",\"data\":null}")))
  @ExceptionHandler({NoSuchElementException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public BaseResponse<Void> notFoundError(HttpServletRequest request, Exception exception) {
    log.error("Not Found Error");
    ServerHttpObservationFilter.findObservationContext(request)
        .ifPresent(context -> context.setError(exception));
    return BaseResponse.of(HttpStatus.NOT_FOUND, exception.getMessage(), null);
  }

  @ApiResponse(responseCode = "400", description = "클라이언트 에러 발생", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"code\":400,\"message\":\"에러메시지가 출력됩니다.\",\"data\":null}")))
  @ExceptionHandler({IllegalArgumentException.class,
      IllegalStateException.class, ServletException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseResponse<Void> clientError(HttpServletRequest request, Exception exception) {
    log.error("client error");
    ServerHttpObservationFilter.findObservationContext(request)
        .ifPresent(context -> context.setError(exception));
    return BaseResponse.clientErrorOf(exception.getMessage());
  }

  @ApiResponse(responseCode = "500", description = "서버 에러 발생", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"code\":500,\"message\":\"에러메시지가 출력됩니다.\",\"data\":null}")))
  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse<Void> serverError(Exception exception) {
    return BaseResponse.serverErrorOf(exception.getMessage());
  }
}
