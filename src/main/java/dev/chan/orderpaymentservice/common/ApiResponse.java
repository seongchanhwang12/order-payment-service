package dev.chan.orderpaymentservice.common;

public record ApiResponse<T> (
        boolean success,
        String message,
        T data,
        ApiError error)
{

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, null , data, null);
    }

    public static <T> ApiResponse<T> failure(T data, ApiError error) {
        return new ApiResponse<>(false,null, null, error);
    }
}
