package dev.chan.orderpaymentservice.web.api;

import dev.chan.orderpaymentservice.common.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {


    /**
     * 정책 위반 예외 핸들러
     * 설명 - 정책 위반 예외에 대한 예외 처리 핸들러입니다.
     * 공통응답 DTO (ApiResponse) 에 오류 정보(ApiError) 를 담아 클라이언트에 응답합니다.
     *
     * @param e - PolicyViolationException.class
     * @return ResponseEntity
     */
    @ExceptionHandler(PolicyViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handle(PolicyViolationException e) {
        ErrorCode errorCode = e.getErrorCode();

        // TODO - messageKey 는 메시지 프로세스 개발 후 실제 메시지로 대체될 예정입니다.
        ApiError apiError = new ApiError(errorCode.status().value(), errorCode.code(), errorCode.messageKey());

        // TODO - 전역 예외처리가 늘어날 경우 공통 예외 응답 메서드로 분리 예정입니다.
        return ResponseEntity.status(errorCode.status())
                .body(ApiResponse.failure(apiError));

    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handle(UnauthorizedException e) {
        ErrorCode errorCode = e.getErrorCode();

        ApiError apiError = new ApiError(errorCode.status().value(), errorCode.code(), e.getMessage());
        return ResponseEntity.status(errorCode.status())
                .body(ApiResponse.failure(apiError));
    }

    /**
     * bean validation 예외 핸들러
     * @param e - MethodArgumentNotValidException.class
     * @return ApiResponse<ApiError>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handle(MethodArgumentNotValidException e){
        List<ErrorDetail> details = e.getBindingResult().getAllErrors().stream().map(err -> {
            if (err instanceof FieldError fieldError) {
                return new ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return new ErrorDetail(err.getObjectName(), err.getDefaultMessage());
        }).toList();

        ApiError apiError = new ApiError(
                CommonError.ARGUMENT_ERROR.status().value(),
                CommonError.ARGUMENT_ERROR.code(),
                CommonError.ARGUMENT_ERROR.messageKey(),
                details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(apiError));
    }

}
