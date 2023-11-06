package leesangho.adtechstudy.mvcapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    private int code;

    private String message;

    private T data;

    public static <T> BaseResponse<T> of(HttpStatus status, String message, T data) {
        return new BaseResponse<>(status.value(), message, data);
    }

    public static <T> BaseResponse<T> ok(String message, T data) {
        return of(HttpStatus.OK, message, data);
    }

    public static <T> BaseResponse<T> createOf(String message, T data) {
        return of(HttpStatus.CREATED, message, data);
    }

    public static <T> BaseResponse<T> clientErrorOf(String message) {
        return of(HttpStatus.BAD_REQUEST, message, null);
    }

    public static <T> BaseResponse<T> serverErrorOf(String message) {
        return of(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
    }
}
