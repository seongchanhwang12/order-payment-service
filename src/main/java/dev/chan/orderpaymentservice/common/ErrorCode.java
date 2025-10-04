package dev.chan.orderpaymentservice.common;

import org.springframework.http.HttpStatus;

/**
 * 공통 에러 인터페이스
 * 코드별 예외 처리가 필요한 에러는 이 인터페이스를 구현합니다.
 */
public interface ErrorCode {
    HttpStatus status();
    String code();
    String messageKey();

}
