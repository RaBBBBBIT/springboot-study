package cn.rabbbbbit.bighomework01.exception;

import cn.rabbbbbit.bighomework01.dto.response.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String msg = exception.getBindingResult().getFieldError() == null
                ? "请求参数校验失败"
                : exception.getBindingResult().getFieldError().getDefaultMessage();
        return Result.failure(400, msg);
    }

    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException exception) {
        String msg = exception.getBindingResult().getFieldError() == null
                ? "参数绑定失败"
                : exception.getBindingResult().getFieldError().getDefaultMessage();
        return Result.failure(400, msg);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Void> handleIllegalArgumentException(IllegalArgumentException exception) {
        return Result.failure(400, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception exception) {
        return Result.failure(500, exception.getMessage());
    }
}
