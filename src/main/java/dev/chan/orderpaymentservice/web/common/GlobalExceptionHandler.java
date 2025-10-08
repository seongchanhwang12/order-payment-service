package dev.chan.orderpaymentservice.web.common;

import dev.chan.orderpaymentservice.common.ApiError;
import dev.chan.orderpaymentservice.common.ApiResponse;
import dev.chan.orderpaymentservice.common.ErrorCode;
import dev.chan.orderpaymentservice.common.PolicyViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 정책 위반 예외 핸들러
     * 설명 - 정책 위반 예외에 대한 예외 처리 핸들러입니다.
     * 공통응답 DTO (ApiResponse) 에 오류 정보(ApiError) 를 담아 클라이언트에 응답합니다.
     *
     * @param e - PolicyViolationException.class
     * @return ResponseEntity
     */
    @ExceptionHandler(PolicyViolationException.class)
    public ResponseEntity<ApiResponse<ApiError>> handle(PolicyViolationException e) {
        ErrorCode errorCode = e.getErrorCode();

        // TODO - messageKey 는 메시지 프로세스 개발 후 실제 메시지로 대체될 예정입니다.
        ApiError apiError = new ApiError(errorCode.status().value(), errorCode.code(), errorCode.messageKey());

        // TODO - 전역 예외처리가 늘어날 경우 공통 예외 응답 메서드로 분리 예정입니다.
        return ResponseEntity.status(errorCode.status())
                .body(ApiResponse.failure(apiError));

    }
}
