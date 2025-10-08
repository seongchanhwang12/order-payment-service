package dev.chan.orderpaymentservice.common;

import lombok.Value;

import java.util.List;

@Value
public class ApiError {

    int status;
    String code;
    String message;
    List<ErrorDetail> details;

    public ApiError(int status, String code, String message) {
        this(status, code, message, null);
    }

    public ApiError(int status, String code, String message, List<ErrorDetail> details) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.details = Ensure.notEmpty(List.copyOf(details), "ApiError.details") ;

    }

}
