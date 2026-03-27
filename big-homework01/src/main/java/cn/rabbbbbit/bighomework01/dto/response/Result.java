package cn.rabbbbbit.bighomework01.dto.response;

public record Result<T>(int code, String msg, T data) {

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    public static <T> Result<T> failure(int code, String msg) {
        return new Result<>(code, msg, null);
    }
}
