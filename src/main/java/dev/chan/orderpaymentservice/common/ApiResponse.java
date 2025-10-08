package dev.chan.orderpaymentservice.common;

/**
 * API 공통 응답 DTO
 * @param success - 성공/실패 여부
 * @param message - 성공 메시지
 * @param data - 성공시 응답 데이터
 * @param error - 실패시 예외 세부 정보
 * @param <T>
 */
public record ApiResponse<T> (
        boolean success,
        String message,
        T data,
        ApiError error)
{

    /**
     * 성공 응답
     * @param data - 예외 데이터
     * @return ApiResponse<T> - 공통 응답 DTO
     * @param <T> - 예외 타입
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, null, data, null);
    }

    /**
     * 데이터 없는 실패
     * @param error - API 예외 응답 정보
     * @return ApiResponse<T>
     */
    public static <T> ApiResponse<T> failure(ApiError error) {
        return new ApiResponse<>(false,null, null, error);
    }

    /**
     * 데이터가 필요한 실패
     *
     * @param data - 예외 세부 정보
     * @param error - API 예외 응답 정보
     * @return ApiResponse<T>
     */
    public static <T> ApiResponse<T> failure(T data, ApiError error) {
        return new ApiResponse<>(false, null, data, error);
    }



}
